package io.github.zohrevand.dialogue.service.xmpp

import android.util.Log
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.packet.MessageBuilder
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.packet.PresenceBuilder
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smackx.chat_markers.ChatMarkersManager
import org.jivesoftware.smackx.chatstates.ChatState
import org.jivesoftware.smackx.chatstates.ChatStateManager
import org.jivesoftware.smackx.mam.MamManager
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager.AutoReceiptMode.always
import org.jxmpp.jid.impl.JidCreate

private const val TAG = "Playground"

// Smack usage gist
class Playground {

    fun testFeatures(connection: XMPPTCPConnection) {

        // XEP-0313: Message Archive Management
        // For getting the history of messages exchanged with peer user
        val mamManager = MamManager.getInstanceFor(connection)
        val queryMostRecentPage =
            mamManager.queryMostRecentPage(JidCreate.bareFrom("zohrevand2@conversations.im"), 500)
        Log.d(TAG, "messages: ${queryMostRecentPage.messages}")

        // XEP-0333: Chat Markers, deferred. Check if the server supports that
        val chatMarkersManager = ChatMarkersManager.getInstanceFor(connection)
        chatMarkersManager.addIncomingChatMarkerMessageListener { chatMarkersState, message, chat ->
            Log.d(
                TAG,
                "addIncomingChatMarkerMessageListener - " +
                    "chatMarkersState: $chatMarkersState, " +
                    "message: $message, " +
                    "chat: $chat"
            )
        }

        // Create ChatManager and Chat for each
        val chatManager = ChatManager.getInstanceFor(connection)
        val currentChat =
            chatManager.chatWith(JidCreate.entityBareFrom("zohrevand2@conversations.im"))

        // Observe incoming and outgoing messages
        chatManager.addIncomingListener { from, message, chat ->
            Log.d(TAG, "addIncomingListener - from: $from, message: $message, chat: $chat")
        }
        chatManager.addOutgoingListener { to, messageBuilder, chat ->
            Log.d(
                TAG,
                "addOutgoingListener - to: $to, messageBuilder: $messageBuilder, chat: $chat"
            )
        }

        // XEP-0085: Chat State Notifications
        // Observe changes to ChatState including: active inactive composing paused gone
        val chatStateManager = ChatStateManager.getInstance(connection)
        chatStateManager.addChatStateListener { chat, state, message ->
            Log.d(TAG, "addChatStateListener - state: $state, message: $message, chat: $chat")
        }
        // Set the current chat state. For example when set to composing,
        // it means user is typing and interacting with the input UI
        chatStateManager.setCurrentState(ChatState.composing, currentChat)

        // XEP-0184: Message Delivery Receipts
        // Observe message delivery, this does not indicate displayed by peer user
        val deliveryReceiptManager = DeliveryReceiptManager.getInstanceFor(connection)
        deliveryReceiptManager.autoReceiptMode = always
        deliveryReceiptManager.addReceiptReceivedListener { fromJid, toJid, receiptId, receipt ->
            Log.d(
                TAG,
                "addReceiptReceivedListener - " +
                    "fromJid: $fromJid, toJid: $toJid, " +
                    "receiptId: $receiptId, receipt: $receipt"
            )
        }

        // Sending message through Chat
        val message = MessageBuilder
            .buildMessage("1234567890")
            .addBody(null, "this is the message")
            .build()
        currentChat.send(message)

        // Sending message without using Chat
        val message2 = MessageBuilder
            .buildMessage("1234567890")
            .to(JidCreate.from("zohrevand3@conversations.im"))
            .addBody(null, "this is the message")
            .build()
        connection.sendStanza(message2)

        // Sending presence stanza, type could be: available unavailable, subscribe, subscribed,
        // unsubscribe, unsubscribed, probe, error
        val presence = PresenceBuilder.buildPresence().ofType(Presence.Type.unavailable)
            .setMode(Presence.Mode.away).build()
        connection.sendStanza(presence)
    }
}
