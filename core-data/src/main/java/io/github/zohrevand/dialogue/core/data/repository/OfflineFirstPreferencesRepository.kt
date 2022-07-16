package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.dialogue.core.data.model.asPreferences
import io.github.zohrevand.dialogue.core.datastore.ConnectionStatus
import io.github.zohrevand.dialogue.core.datastore.DialoguePreferencesDataSource
import io.github.zohrevand.dialogue.core.datastore.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstPreferencesRepository @Inject constructor(
    private val preferencesDataSource: DialoguePreferencesDataSource,
) : PreferencesRepository {

    override fun getConnectionStatus(): Flow<ConnectionStatus> =
        preferencesDataSource.getConnectionStatus()

    override suspend fun updateConnectionStatus(connectionStatus: ConnectionStatus) =
        preferencesDataSource.updateConnectionStatus(connectionStatus)

    override fun getAccount(): Flow<Account> =
        preferencesDataSource.getAccount().map { it.asExternalModel() }

    override suspend fun updateAccount(account: Account) =
        preferencesDataSource.updateAccount(account.asPreferences())
}