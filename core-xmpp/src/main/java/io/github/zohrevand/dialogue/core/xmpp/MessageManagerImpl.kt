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
import org.jxmpp.jid.impl.JidCreate
import javax.inject.Inject

private const val TAG = "MessagesManagerImpl"

class MessageManagerImpl @Inject constructor(
    private val messagesCollector: MessagesCollector
) : MessageManager {

    private val scope = CoroutineScope(SupervisorJob())

    private lateinit var chatManager: ChatManager

    override suspend fun initialize(connection: XMPPTCPConnection) {
        chatManager = ChatManager.getInstanceFor(connection)

        scope.launch {
            messagesCollector.collectShouldSendMessages(sendMessages = ::sendMessages)
        }

        observeIncomingMessages()

        observeOutgoingMessages()
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
