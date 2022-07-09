package io.github.zohrevand.dialogue.core.xmpp.collector

import io.github.zohrevand.core.model.data.Account

interface AccountsCollector {
    /**
    * Collects the changes to accounts stream originated from database
    * */
    suspend fun collectAccounts(
        onNewLogin: (Account) -> Unit,
    )
}