package io.github.zohrevand.dialogue.core.xmpp.collector

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.core.model.data.AccountStatus.LoggingIn
import io.github.zohrevand.core.model.data.AccountStatus.PreLoggingIn
import io.github.zohrevand.core.model.data.AccountStatus.PreRegistering
import io.github.zohrevand.core.model.data.AccountStatus.Registering
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import javax.inject.Inject

class AccountsCollectorImpl @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : AccountsCollector {

    override suspend fun collectAccounts(
        onNewLogin: suspend (Account) -> Unit,
        onNewRegister: suspend (Account) -> Unit
    ) {
        preferencesRepository.getAccount().collect { account ->
            if (account.status == PreLoggingIn) {
                onNewLogin(account)
                preferencesRepository.updateAccount(account.copy(status = LoggingIn))
            }
            if (account.status == PreRegistering) {
                onNewRegister(account)
                preferencesRepository.updateAccount(account.copy(status = Registering))
            }
        }
    }
}
