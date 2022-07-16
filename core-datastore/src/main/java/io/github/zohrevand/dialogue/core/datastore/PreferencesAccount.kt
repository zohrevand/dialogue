package io.github.zohrevand.dialogue.core.datastore

import io.github.zohrevand.dialogue.core.datastore.UserPreferences.AccountStatus
import io.github.zohrevand.dialogue.core.datastore.UserPreferences.AccountStatus.Disabled

data class PreferencesAccount(
    val jid: String = "",
    val localPart: String = "",
    val domainPart: String = "",
    val password: String = "",
    val status: AccountStatus = Disabled
)