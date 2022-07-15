package io.github.zohrevand.dialogue.core.datastore

import io.github.zohrevand.core.model.data.AccountStatus
import io.github.zohrevand.core.model.data.AccountStatus.Disabled

data class PreferencesAccount(
    val jid: String = "",
    val username: String = "",
    val domain: String = "",
    val password: String = "",
    val status: AccountStatus = Disabled
)
