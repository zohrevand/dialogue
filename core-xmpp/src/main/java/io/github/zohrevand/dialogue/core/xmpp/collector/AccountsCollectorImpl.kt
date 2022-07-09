package io.github.zohrevand.dialogue.core.xmpp.collector

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.core.model.data.AccountStatus.LoggingIn
import io.github.zohrevand.core.model.data.AccountStatus.PreLoggingIn
import io.github.zohrevand.dialogue.core.data.repository.AccountsRepository
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class AccountsCollectorImpl @Inject constructor(
    private val accountsRepository: AccountsRepository
) : AccountsCollector {

    override suspend fun collectAccounts(
        onNewLogin: (Account) -> Unit,
    ) {
        accountsRepository.getAccountsStream().collectLatest { accounts ->
            val preLoggingInAccount = accounts.firstOrNull { it.status == PreLoggingIn }
            preLoggingInAccount?.let {
                onNewLogin(it)
                accountsRepository.updateAccount(it.copy(status = LoggingIn))
            }

        }
    }
}
