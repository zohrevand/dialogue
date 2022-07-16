package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.dialogue.core.datastore.ConnectionStatus
import kotlinx.coroutines.flow.Flow

class OfflineFirstPreferencesRepository : PreferencesRepository {
    override fun getConnectionStatus(): Flow<ConnectionStatus> {
        TODO("Not yet implemented")
    }

    override suspend fun updateConnectionStatus(connectionStatus: ConnectionStatus) {
        TODO("Not yet implemented")
    }

    override fun getAccount(): Flow<Account> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAccount(account: Account) {
        TODO("Not yet implemented")
    }
}