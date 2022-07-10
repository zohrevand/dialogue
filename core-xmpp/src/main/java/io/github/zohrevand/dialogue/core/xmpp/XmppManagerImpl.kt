package io.github.zohrevand.dialogue.core.xmpp

import io.github.zohrevand.core.model.data.Account
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jivesoftware.smack.ReconnectionManager
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import javax.inject.Inject

class XmppManagerImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher
) : XmppManager {

    private var xmppConnection: XMPPTCPConnection? = null

    override fun getConnection(): XMPPTCPConnection =
        xmppConnection ?: throw NoSuchElementException("Connection is not established.")

    override suspend fun login(account: Account) {
        xmppConnection = account.login(
            configurationBuilder = ::getConfiguration,
            connectionBuilder = ::XMPPTCPConnection,
            reconnectionManager = ::configureReconnectionManager
        )
    }

    override suspend fun register(account: Account) {
        TODO("Not yet implemented")
    }

    private suspend fun Account.login(
        configurationBuilder: (Account) -> XMPPTCPConnectionConfiguration,
        connectionBuilder: (XMPPTCPConnectionConfiguration) -> XMPPTCPConnection,
        reconnectionManager: (XMPPTCPConnection) -> Unit
    ): XMPPTCPConnection {
        val configuration = configurationBuilder(this)
        val connection = connectionBuilder(configuration)

        reconnectionManager(connection)

        return connection.connectAndLogin()
    }

    private fun getConfiguration(account: Account): XMPPTCPConnectionConfiguration =
        XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword(account.username, account.password)
            .setXmppDomain(account.domain)
            .build()

    // TODO: this warning is fixed as of IntelliJ 2022.1
    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun XMPPTCPConnection.connectAndLogin(): XMPPTCPConnection =
        withContext(ioDispatcher) {
            connect()
            login()
            this@connectAndLogin
        }

    private fun configureReconnectionManager(connection: XMPPTCPConnection) {
        ReconnectionManager.getInstanceFor(connection)
            .enableAutomaticReconnection()
    }
}
