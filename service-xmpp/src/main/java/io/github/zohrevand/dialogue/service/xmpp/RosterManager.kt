package io.github.zohrevand.dialogue.service.xmpp

import org.jivesoftware.smack.tcp.XMPPTCPConnection

interface RosterManager {

    suspend fun initialize(connection: XMPPTCPConnection)

    fun onCleared()
}
