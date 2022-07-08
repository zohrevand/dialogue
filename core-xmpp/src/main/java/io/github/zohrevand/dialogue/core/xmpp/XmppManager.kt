package io.github.zohrevand.dialogue.core.xmpp

import org.jivesoftware.smack.tcp.XMPPTCPConnection

interface XmppManager {

    suspend fun getConnection(): XMPPTCPConnection
}
