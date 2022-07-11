package io.github.zohrevand.dialogue.core.data.testdoubles

import io.github.zohrevand.core.model.data.AccountStatus
import io.github.zohrevand.dialogue.core.database.dao.AccountDao
import io.github.zohrevand.dialogue.core.database.model.AccountEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update

/**
 * Test double for [AccountDao]
 */
class TestAccountDao : AccountDao {
    private var entitiesStateFlow = MutableStateFlow(
        listOf(
            AccountEntity(
                id = 1L,
                username = "hasan",
                domain = "server.com",
                password = "1234",
                displayName = "Hasan",
                resource = "iphone",
                status = AccountStatus.LoggingIn,
            )
        )
    )

    override fun getAccountEntity(accountId: Long): Flow<AccountEntity> =
        entitiesStateFlow.mapNotNull { accounts -> accounts.firstOrNull { it.id == accountId } }

    override fun getAccountEntitiesStream(): Flow<List<AccountEntity>> =
        entitiesStateFlow

    override suspend fun insertOrIgnoreAccount(entity: AccountEntity) {
        entitiesStateFlow.value = entitiesStateFlow.value + entity
    }

    override suspend fun updateAccount(entity: AccountEntity) {
        entitiesStateFlow.update { entities ->
            entities.map {
                if (it.id == entity.id) entity else it
            }
        }
    }

    override suspend fun deleteAccount(id: Long) {
        entitiesStateFlow.update { entities ->
            entities.filterNot { it.id == id }
        }
    }

    override suspend fun deleteAllAccounts() {
        TODO("Not yet implemented")
    }
}