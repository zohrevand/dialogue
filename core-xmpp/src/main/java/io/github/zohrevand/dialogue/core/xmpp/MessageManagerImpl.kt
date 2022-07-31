package io.github.zohrevand.dialogue.core.xmpp

import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import javax.inject.Inject

class MessageManagerImpl @Inject constructor(

) : MessageManager {

    private lateinit var chatManager: ChatManager

    override suspend fun initialize(connection: XMPPTCPConnection) {
        chatManager = ChatManager.getInstanceFor(connection)
    }
}
