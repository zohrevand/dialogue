package io.github.zohrevand.dialogue.core.datastore

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.dialogue.core.datastore.UserPreferences.AccountStatus
import io.github.zohrevand.dialogue.core.datastore.UserPreferences.AccountStatus.Disabled

data class PreferencesAccount(
    val jid: String = "",
    val localPart: String = "",
    val domainPart: String = "",
    val password: String = "",
    val status: AccountStatus = Disabled
)

fun PreferencesAccount.asExternalModel() = Account(
    jid = jid,
    localPart = localPart,
    domainPart = domainPart,
    password = password,
    status = io.github.zohrevand.core.model.data.AccountStatus.valueOf(status.name)
)
