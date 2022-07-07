package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Account
import kotlinx.coroutines.flow.Flow

class OfflineFirstAccountsRepository(

) : AccountsRepository {
    override fun getAccountsStream(): Flow<List<Account>> {
        TODO("Not yet implemented")
    }

    override fun getAccount(id: String): Flow<Account> {
        TODO("Not yet implemented")
    }

    override suspend fun addAccount(account: Account) {
        TODO("Not yet implemented")
    }

    override suspend fun updateAccount(account: Account) {
        TODO("Not yet implemented")
    }
}