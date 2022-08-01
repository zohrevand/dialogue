package io.github.zohrevand.dialogue.core.xmpp

import android.util.Log
import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.dialogue.core.xmpp.collector.MessagesCollector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener
import org.jivesoftware.smack.packet.MessageBuilder
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smackx.chatstates.ChatStateListener
import org.jivesoftware.smackx.chatstates.ChatStateManager
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager.AutoReceiptMode.always
import org.jivesoftware.smackx.receipts.ReceiptReceivedListener
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.impl.JidCreate
import javax.inject.Inject

private const val TAG = "MessagesManagerImpl"

class MessageManagerImpl @Inject constructor(
    private val messagesCollector: MessagesCollector
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

        observeIncomingMessages()

        observeOutgoingMessages()

        observeChatState()

        observeDeliveryReceipt()
    }

    private fun observeIncomingMessages() {
        incomingChatMessageListener = IncomingChatMessageListener { from, message, chat ->
            Log.d(TAG, "IncomingListener - from: $from, message: $message, chat: $chat")
        }
        chatManager.addIncomingListener(incomingChatMessageListener)
    }

    private fun observeOutgoingMessages() {
        outgoingChatMessageListener = OutgoingChatMessageListener { to, messageBuilder, chat ->
            Log.d(TAG, "OutgoingListener - to: $to, messageBuilder: $messageBuilder, chat: $chat")
        }
        chatManager.addOutgoingListener(outgoingChatMessageListener)
    }

    private fun observeChatState() {
        chatStateListener = ChatStateListener { chat, state, message ->
            Log.d(TAG, "ChatStateListener - state: $state, message: $message, chat: $chat")
        }
        chatStateManager.addChatStateListener(chatStateListener)
    }

    private fun observeDeliveryReceipt() {
        deliveryReceiptManager.autoReceiptMode = always
        receiptReceivedListener = ReceiptReceivedListener { fromJid, toJid, receiptId, receipt ->
            Log.d(
                TAG,
                "addReceiptReceivedListener - fromJid: $fromJid, toJid: $toJid, receiptId: $receiptId, receipt: $receipt"
            )
        }
        deliveryReceiptManager.addReceiptReceivedListener(receiptReceivedListener)
    }

    // blocking
    private fun sendMessages(messages: List<Message>) {
        messages.forEach { message ->
            val chat = chatManager.chatWith(JidCreate.entityBareFrom(message.peerJid))
            val smackMessage = MessageBuilder
                .buildMessage()
                .addBody(null, message.body)
                .build()

            chat.send(smackMessage)
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
