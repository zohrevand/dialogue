package io.github.zohrevand.dialogue.core.data.model

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.core.model.data.AccountStatus.Disabled
import io.github.zohrevand.dialogue.core.database.model.AccountEntity
import io.github.zohrevand.dialogue.core.datastore.PreferencesAccount
import io.github.zohrevand.dialogue.core.datastore.UserPreferences

// TODO: this should be removed
fun Account.asEntity() = AccountEntity(
    id = 0,
    username = "",
    domain = "",
    password = "",
    displayName = "",
    resource = "",
    status = Disabled
)

fun Account.asPreferences() = PreferencesAccount(
    jid = jid,
    localPart = localPart,
    domainPart = domainPart,
    password = password,
    status = UserPreferences.AccountStatus.valueOf(status.name)
)
