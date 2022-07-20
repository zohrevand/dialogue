package io.github.zohrevand.dialogue.core.xmpp

import org.jivesoftware.smack.tcp.XMPPTCPConnection

interface RosterManager {

    suspend fun initializeRoster(connection: XMPPTCPConnection)

    fun onCleared()
}
