package io.github.zohrevand.dialogue.core.data.model

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.dialogue.core.datastore.PreferencesAccount
import io.github.zohrevand.dialogue.core.datastore.UserPreferences

fun Account.asPreferences() = PreferencesAccount(
    jid = jid,
    localPart = localPart,
    domainPart = domainPart,
    password = password,
    status = UserPreferences.AccountStatus.valueOf(status.name)
)
