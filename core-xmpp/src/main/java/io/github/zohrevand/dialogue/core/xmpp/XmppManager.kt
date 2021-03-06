package io.github.zohrevand.dialogue.core.xmpp

import io.github.zohrevand.core.model.data.Account
import org.jivesoftware.smack.tcp.XMPPTCPConnection

interface XmppManager {

    suspend fun initialize()

    fun getConnection(): XMPPTCPConnection

    suspend fun login(account: Account)

    suspend fun register(account: Account)

    fun onCleared()
}
