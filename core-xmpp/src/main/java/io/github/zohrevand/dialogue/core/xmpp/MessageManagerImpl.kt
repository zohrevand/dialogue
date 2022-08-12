package io.github.zohrevand.dialogue.core.xmpp

import android.util.Log
import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.core.model.data.MessageStatus.Sent
import io.github.zohrevand.core.model.data.MessageStatus.SentDelivered
import io.github.zohrevand.core.model.data.SendingChatState
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import io.github.zohrevand.dialogue.core.data.repository.MessagesRepository
import io.github.zohrevand.dialogue.core.xmpp.collector.ChatStateCollector
import io.github.zohrevand.dialogue.core.xmpp.collector.MessagesCollector
import io.github.zohrevand.dialogue.core.xmpp.model.asConversation
import io.github.zohrevand.dialogue.core.xmpp.model.asExternalEnum
import io.github.zohrevand.dialogue.core.xmpp.model.asExternalModel
import io.github.zohrevand.dialogue.core.xmpp.model.asSmackEnum
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener
import org.jivesoftware.smack.packet.Message as SmackMessage
import org.jivesoftware.smack.packet.MessageBuilder
import org.jivesoftware.smack.packet.Stanza
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smackx.chatstates.ChatState
import org.jivesoftware.smackx.chatstates.ChatStateListener
import org.jivesoftware.smackx.chatstates.ChatStateManager
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager.AutoReceiptMode.always
import org.jivesoftware.smackx.receipts.ReceiptReceivedListener
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.Jid
import org.jxmpp.jid.impl.JidCreate

private const val TAG = "MessagesManagerImpl"

class MessageManagerImpl @Inject constructor(
    private val messagesCollector: MessagesCollector,
    private val chatStateCollector: ChatStateCollector,
    private val messagesRepository: MessagesRepository,
    private val conversationsRepository: ConversationsRepository
) : MessageManager {

    private val scope = CoroutineScope(SupervisorJob())

    private lateinit var chatManager: ChatManager
    private lateinit var chatStateManager: ChatStateManager
    private lateinit var deliveryReceiptManager: DeliveryReceiptManager

    private var incomingChatMessageListener: IncomingChatMessageListener? = null
    private var outgoingChatMessageListener: OutgoingChatMessageListener? = null
    private var chatStateListener: ChatStateListener? = null
    private var receiptReceivedListener: ReceiptReceivedListener? = null

    override suspend fun initialize(connection: XMPPTCPConnection) {
        chatManager = ChatManager.getInstanceFor(connection)
        chatStateManager = ChatStateManager.getInstance(connection)
        deliveryReceiptManager = DeliveryReceiptManager.getInstanceFor(connection)

        scope.launch {
            messagesCollector.collectShouldSendMessages(sendMessages = ::sendMessages)
        }

        scope.launch {
            chatStateCollector.collectChatState(onChatStateChanged = ::sendChatState)
        }

        observeIncomingMessages()
        observeOutgoingMessages()
        observeChatState()
        observeDeliveryReceipt()
    }

    // blocking
    private fun sendMessages(messages: List<Message>) {
        messages.forEach { message ->
            val chat = chatManager.chatWith(JidCreate.entityBareFrom(message.peerJid))
            val smackMessage = MessageBuilder
                .buildMessage(message.stanzaId)
                .addBody(null, message.body)
                .build()

            chat.send(smackMessage)
        }
    }

    // blocking
    private fun sendChatState(sendingChatState: SendingChatState) {
        val chat = chatManager.chatWith(JidCreate.entityBareFrom(sendingChatState.peerJid))
        chatStateManager.setCurrentState(sendingChatState.chatState.asSmackEnum(), chat)
    }

    private fun observeIncomingMessages() {
        incomingChatMessageListener = IncomingChatMessageListener(::handleIncomingMessage)
        chatManager.addIncomingListener(incomingChatMessageListener)
    }

    private fun observeOutgoingMessages() {
        outgoingChatMessageListener = OutgoingChatMessageListener(::handleOutgoingMessage)
        chatManager.addOutgoingListener(outgoingChatMessageListener)
    }

    private fun observeChatState() {
        chatStateListener = ChatStateListener(::handleChatState)
        chatStateManager.addChatStateListener(chatStateListener)
    }

    private fun observeDeliveryReceipt() {
        deliveryReceiptManager.autoReceiptMode = always
        deliveryReceiptManager.autoAddDeliveryReceiptRequests()
        receiptReceivedListener = ReceiptReceivedListener(::handleReceivedReceipt)
        deliveryReceiptManager.addReceiptReceivedListener(receiptReceivedListener)
    }

    private fun handleIncomingMessage(
        from: EntityBareJid,
        message: SmackMessage,
        chat: Chat
    ) {
        Log.d(TAG, "IncomingListener - from: $from, message: $message, chat: $chat")

        scope.launch {
            val conversation = conversationsRepository.getConversation(from.toString()).first()
                ?: from.asConversation()

            messagesRepository.updateMessage(message.asExternalModel())

            val lastMessage = messagesRepository.getMessageByStanzaId(message.stanzaId).first()
            val unreadMessagesCount = conversation.unreadMessagesCount + 1

            conversationsRepository.updateConversation(
                conversation.copy(
                    lastMessage = lastMessage,
                    unreadMessagesCount = unreadMessagesCount
                )
            )
        }
    }

    private fun handleOutgoingMessage(
        to: EntityBareJid,
        messageBuilder: MessageBuilder,
        chat: Chat
    ) {
        Log.d(TAG, "OutgoingListener - to: $to, messageBuilder: $messageBuilder, chat: $chat")

        scope.launch {
            // TODO: if user has multiple clients this will return null because stanzaId
            //  does not exist for other clients
            val message = messagesRepository.getMessageByStanzaId(messageBuilder.stanzaId).first()
            requireNotNull(message) { "Message must not be null" }

            messagesRepository.updateMessage(message.copy(status = Sent))

            val conversation = conversationsRepository.getConversation(message.peerJid).first()
            requireNotNull(conversation) { "Conversation must not be null" }

            conversationsRepository.updateConversation(conversation.copy(lastMessage = message))
        }
    }

    private fun handleChatState(
        chat: Chat,
        state: ChatState,
        message: SmackMessage
    ) {
        Log.d(TAG, "ChatStateListener - state: $state, message: $message, chat: $chat")

        scope.launch {
            val peerJid = chat.xmppAddressOfChatPartner.toString()
            val conversation = conversationsRepository.getConversation(peerJid).first()
            conversation?.let {
                conversationsRepository.updateConversation(
                    it.copy(chatState = state.asExternalEnum())
                )
            }
        }
    }

    private fun handleReceivedReceipt(
        fromJid: Jid,
        toJid: Jid,
        receiptId: String,
        receipt: Stanza
    ) {
        Log.d(
            TAG,
            "addReceiptReceivedListener - " +
                "fromJid: $fromJid, toJid: $toJid, " +
                "receiptId: $receiptId, receipt: $receipt"
        )

        scope.launch {
            // TODO: if user has multiple clients this will return null because stanzaId
            //  does not exist for other clients
            val message = messagesRepository.getMessageByStanzaId(receiptId).first()
            message?.let {
                messagesRepository.updateMessage(it.copy(status = SentDelivered))
            }
        }
    }

    override fun onCleared() {
        scope.cancel()
        if (this::chatManager.isInitialized) {
            chatManager.removeIncomingListener(incomingChatMessageListener)
            chatManager.removeOutgoingListener(outgoingChatMessageListener)
        }
        if (this::chatStateManager.isInitialized) {
            chatStateManager.removeChatStateListener(chatStateListener)
        }
        if (this::deliveryReceiptManager.isInitialized) {
            deliveryReceiptManager.removeReceiptReceivedListener(receiptReceivedListener)
        }
    }
}
