package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.dialogue.core.datastore.PreferencesConnectionStatus
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun getConnectionStatus(): Flow<PreferencesConnectionStatus>

    suspend fun updateConnectionStatus(connectionStatus: PreferencesConnectionStatus)

    fun getAccount(): Flow<Account>

    suspend fun updateAccount(account: Account)
}