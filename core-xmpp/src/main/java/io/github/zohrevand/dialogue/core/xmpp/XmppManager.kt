package io.github.zohrevand.dialogue.core.xmpp

import io.github.zohrevand.core.model.data.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.jivesoftware.smack.tcp.XMPPTCPConnection

interface XmppManager {

    val isAuthenticatedState: StateFlow<Boolean>

    fun getConnection(): XMPPTCPConnection

    suspend fun login(account: Account)

    suspend fun register(account: Account)
}
