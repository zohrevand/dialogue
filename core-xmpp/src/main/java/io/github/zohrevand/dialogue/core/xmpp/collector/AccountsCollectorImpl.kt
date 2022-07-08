package io.github.zohrevand.dialogue.core.xmpp.collector

import io.github.zohrevand.dialogue.core.data.repository.AccountsRepository
import javax.inject.Inject

class AccountsCollectorImpl @Inject constructor(
    private val accountsRepository: AccountsRepository
) : AccountsCollector {

    override fun collectAccounts() {
        TODO("Not yet implemented")
    }
}