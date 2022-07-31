package io.github.zohrevand.dialogue.core.xmpp

import org.jivesoftware.smack.tcp.XMPPTCPConnection

interface MessageManager {

    suspend fun initialize(connection: XMPPTCPConnection)

    fun onCleared()
}
