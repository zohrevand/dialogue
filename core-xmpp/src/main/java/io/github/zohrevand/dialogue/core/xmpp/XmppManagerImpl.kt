package io.github.zohrevand.dialogue.core.xmpp

import android.util.Log
import io.github.zohrevand.core.model.data.Account
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.ReconnectionManager
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import javax.inject.Inject

private const val TAG = "XmppManagerImpl"

class XmppManagerImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher
) : XmppManager {

    private var xmppConnection: XMPPTCPConnection? = null

    private val _isAuthenticatedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isAuthenticatedState: StateFlow<Boolean> = _isAuthenticatedState

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
            _isAuthenticatedState.value = isAuthenticated
            Log.d(TAG, "isConnected: $isConnected")
            Log.d(TAG, "isAuthenticated: $isAuthenticated")
            this@connectAndLogin.addConnectionListener(object : ConnectionListener {
                override fun connecting(connection: XMPPConnection?) {
                    Log.d(TAG, "connecting...")
                }

                override fun connected(connection: XMPPConnection?) {
                    Log.d(TAG, "connected from listener")
                }

                override fun authenticated(connection: XMPPConnection?, resumed: Boolean) {
                    Log.d(TAG, "authenticated from listener, resumed: $resumed")
                }

                override fun connectionClosed() {
                    Log.d(TAG, "connectionClosed")
                }

                override fun connectionClosedOnError(e: Exception?) {
                    Log.d(TAG, "connectionClosedOnError")
                }
            })
            this@connectAndLogin
        }

    private fun configureReconnectionManager(connection: XMPPTCPConnection) {
        ReconnectionManager.getInstanceFor(connection)
            .enableAutomaticReconnection()
    }

    override fun onCleared() {
        TODO("Not yet implemented")
    }
}
