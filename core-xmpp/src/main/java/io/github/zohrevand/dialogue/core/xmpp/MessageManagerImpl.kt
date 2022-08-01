package io.github.zohrevand.dialogue.core.xmpp

import android.util.Log
import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.dialogue.core.xmpp.collector.MessagesCollector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.packet.MessageBuilder
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smackx.chatstates.ChatStateManager
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager.AutoReceiptMode.always
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
        chatManager.addIncomingListener { from, message, chat ->
            Log.d(TAG, "IncomingListener - from: $from, message: $message, chat: $chat")
        }
    }

    private fun observeOutgoingMessages() {
        chatManager.addOutgoingListener { to, messageBuilder, chat ->
            Log.d(TAG, "OutgoingListener - to: $to, messageBuilder: $messageBuilder, chat: $chat")
        }
    }

    private fun observeChatState() {
        chatStateManager.addChatStateListener { chat, state, message ->
            Log.d(TAG, "ChatStateListener - state: $state, message: $message, chat: $chat")
        }
    }

    private fun observeDeliveryReceipt() {
        deliveryReceiptManager.autoReceiptMode = always
        deliveryReceiptManager.addReceiptReceivedListener { fromJid, toJid, receiptId, receipt ->
            Log.d(TAG, "addReceiptReceivedListener - fromJid: $fromJid, toJid: $toJid, receiptId: $receiptId, receipt: $receipt")
        }
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
    }
}
