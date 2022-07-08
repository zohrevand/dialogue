package io.github.zohrevand.dialogue.core.xmpp

import io.github.zohrevand.core.model.data.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import javax.inject.Inject

class XmppManagerImpl @Inject constructor() : XmppManager {

    private var xmppConnection: XMPPTCPConnection? = null

    override suspend fun getConnection(): XMPPTCPConnection =
        xmppConnection ?: throw NoSuchElementException("Connection is not established.")

    override suspend fun login(account: Account) {
        xmppConnection = account.login(
            configurationBuilder = ::getConfiguration,
            connectionBuilder = ::XMPPTCPConnection
        )
    }
}

private suspend fun Account.login(
    configurationBuilder: (Account) -> XMPPTCPConnectionConfiguration,
    connectionBuilder: (XMPPTCPConnectionConfiguration) -> XMPPTCPConnection,
): XMPPTCPConnection {
    val configuration = configurationBuilder(this)
    val connection = connectionBuilder(configuration)

    val list = listOf<String>()
    list.first()

    return withContext(Dispatchers.IO) {
        return@withContext connection.connectAndLogin()
    }
}

private fun getConfiguration(account: Account): XMPPTCPConnectionConfiguration =
    XMPPTCPConnectionConfiguration.builder()
        .setUsernameAndPassword(account.username, account.password)
        .setXmppDomain(account.domain)
        .build()

/**
* This call blocks
* */
private fun XMPPTCPConnection.connectAndLogin(): XMPPTCPConnection {
    connect()
    login()
    return this
}