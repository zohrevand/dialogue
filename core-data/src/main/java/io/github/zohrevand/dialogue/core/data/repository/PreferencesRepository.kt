package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.core.model.data.ConnectionStatus
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun getConnectionStatus(): Flow<ConnectionStatus>

    suspend fun updateConnectionStatus(connectionStatus: ConnectionStatus)

    fun getAccount(): Flow<Account>

    suspend fun updateAccount(account: Account)
}
