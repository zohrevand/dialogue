package io.github.zohrevand.dialogue.core.xmpp

import org.jivesoftware.smack.tcp.XMPPTCPConnection

interface RosterManager {

    fun initializeRoster(connection: XMPPTCPConnection)
}