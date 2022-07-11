package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.dialogue.core.data.testdoubles.TestAccountDao
import io.github.zohrevand.dialogue.core.database.dao.AccountDao
import io.github.zohrevand.dialogue.core.database.model.AccountEntity
import io.github.zohrevand.dialogue.core.database.model.asExternalModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OfflineFirstAccountsRepositoryTest {

    private lateinit var subject: OfflineFirstAccountsRepository

    private lateinit var accountDao: AccountDao

    @Before
    fun setup() {
        accountDao = TestAccountDao()

        subject = OfflineFirstAccountsRepository(
            accountDao = accountDao
        )
    }

    @Test
    fun offlineFirstAccountsRepository_accounts_stream_is_backed_by_accounts_dao() {
        runTest {
            Assert.assertEquals(
                accountDao.getAccountEntitiesStream()
                    .first()
                    .map(AccountEntity::asExternalModel),
                subject.getAccountsStream()
                    .first()
            )
        }
    }
}