package io.github.zohrevand.dialogue.core.xmpp

import io.github.zohrevand.core.model.data.Account
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import javax.inject.Inject

class XmppManagerImpl @Inject constructor() : XmppManager {

    private var xmppConnection: XMPPTCPConnection? = null

    override suspend fun getConnection(): XMPPTCPConnection =
        xmppConnection ?: throw NoSuchElementException("Connection is not established.")
}

private fun getConfiguration(account: Account): XMPPTCPConnectionConfiguration =
    XMPPTCPConnectionConfiguration.builder()
        .setUsernameAndPassword(account.username, account.password)
        .setXmppDomain(account.domain)
        .build()
