package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.dialogue.core.data.model.asEntity
import io.github.zohrevand.dialogue.core.database.dao.AccountDao
import io.github.zohrevand.dialogue.core.database.model.AccountEntity
import io.github.zohrevand.dialogue.core.database.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstAccountsRepository @Inject constructor(
    private val accountDao: AccountDao
) : AccountsRepository {

    override fun getAccountsStream(): Flow<List<Account>> =
        accountDao.getAccountEntitiesStream()
            .map { it.map(AccountEntity::asExternalModel) }

    override fun getAccount(id: Long): Flow<Account> =
        accountDao.getAccountEntity(id)
            .map(AccountEntity::asExternalModel)

    override suspend fun addAccount(account: Account) {
        accountDao.insertOrIgnoreAccount(account.asEntity())
    }

    override suspend fun updateAccount(account: Account) {
        accountDao.updateAccount(account.asEntity())
    }

    override suspend fun deleteAllAccounts() {
        accountDao.deleteAllAccounts()
    }
}