package io.github.zohrevand.dialogue.core.xmpp

import io.github.zohrevand.core.model.data.Account
import kotlinx.coroutines.flow.Flow
import org.jivesoftware.smack.tcp.XMPPTCPConnection

interface XmppManager {

    fun getConnection(): XMPPTCPConnection

    fun getAuthenticatedStream(): Flow<Boolean>

    suspend fun login(account: Account)

    suspend fun register(account: Account)
}
