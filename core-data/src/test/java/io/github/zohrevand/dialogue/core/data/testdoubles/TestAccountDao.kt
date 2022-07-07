package io.github.zohrevand.dialogue.core.data.testdoubles

import io.github.zohrevand.core.model.data.AccountStatus
import io.github.zohrevand.dialogue.core.database.dao.AccountDao
import io.github.zohrevand.dialogue.core.database.model.AccountEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Test double for [AccountDao]
 */
class TestAccountDao : AccountDao {
    private var entitiesStateFlow = MutableStateFlow(
        listOf(
            AccountEntity(
                id = "1",
                username = "hasan",
                domain = "server.com",
                password = "1234",
                displayName = "Hasan",
                resource = "iphone",
                status = AccountStatus.LoggingIn,
            )
        )
    )

    override fun getAccountEntity(accountId: String): Flow<AccountEntity> {
        TODO("Not yet implemented")
    }

    override fun getAccountEntitiesStream(): Flow<List<AccountEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrIgnoreAccount(entity: AccountEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateAccount(entity: AccountEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount(id: String) {
        TODO("Not yet implemented")
    }
}