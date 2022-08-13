package io.github.zohrevand.dialogue.service.xmpp.collector

import io.github.zohrevand.core.model.data.Account

interface AccountsCollector {
    /**
     * Collects the changes to accounts stream originated from database
     * */
    suspend fun collectAccounts(
        onNewLogin: suspend (Account) -> Unit,
        onNewRegister: suspend (Account) -> Unit
    )
}
