package io.github.zohrevand.dialogue.core.xmpp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import io.github.zohrevand.dialogue.core.datastore.DialoguePreferencesDataSource
import io.github.zohrevand.dialogue.core.xmpp.collector.AccountsCollector
import io.github.zohrevand.dialogue.core.xmpp.notification.NotificationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class XmppService : Service() {

    private val scope = CoroutineScope(SupervisorJob())

    @Inject
    lateinit var xmppManager: XmppManager

    @Inject
    lateinit var preferencesDataSource: DialoguePreferencesDataSource

    @Inject
    lateinit var accountsCollector: AccountsCollector

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        scope.launch {
            initializeXmppManager()
            observeConnectionStatus()
            observeAccountsStream()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private suspend fun initializeXmppManager() {
        xmppManager.setDefaultConnectionStatus()
    }

    private suspend fun observeConnectionStatus() {
        preferencesDataSource.getConnectionStatus().collect { connectionStatus ->
            if (connectionStatus.availability && connectionStatus.authorized) {
                val notification = notificationManager.getNotification(
                    title = "Dialogue Xmpp Service",
                    text = "You are connected"
                )
                startForeground(1000, notification)
            }
        }
    }

    private suspend fun observeAccountsStream() {
        accountsCollector.collectAccounts(
            onNewLogin = { xmppManager.login(it) },
            onNewRegister = { xmppManager.register(it) },
        )
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onDestroy() {
        scope.cancel()
        xmppManager.onCleared()
        super.onDestroy()
    }
}
