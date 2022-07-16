package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.dialogue.core.datastore.ConnectionStatus
import io.github.zohrevand.dialogue.core.datastore.DialoguePreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstPreferencesRepository @Inject constructor(
    private val preferencesDataSource: DialoguePreferencesDataSource,
) : PreferencesRepository {

    override fun getConnectionStatus(): Flow<ConnectionStatus> =
        preferencesDataSource.getConnectionStatus()

    override suspend fun updateConnectionStatus(connectionStatus: ConnectionStatus) =
        preferencesDataSource.updateConnectionStatus(connectionStatus)

    override fun getAccount(): Flow<Account> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAccount(account: Account) {
        TODO("Not yet implemented")
    }
}