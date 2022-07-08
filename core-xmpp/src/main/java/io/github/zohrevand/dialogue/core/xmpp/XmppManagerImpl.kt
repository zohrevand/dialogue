package io.github.zohrevand.dialogue.core.xmpp

import org.jivesoftware.smack.tcp.XMPPTCPConnection
import javax.inject.Inject

class XmppManagerImpl @Inject constructor() : XmppManager {

    private var xmppConnection: XMPPTCPConnection? = null

    override suspend fun getConnection(): XMPPTCPConnection =
        xmppConnection ?: throw NoSuchElementException("Connection is not established.")
}
