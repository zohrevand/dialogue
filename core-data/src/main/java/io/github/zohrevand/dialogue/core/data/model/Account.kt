package io.github.zohrevand.dialogue.core.data.model

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.dialogue.core.database.model.AccountEntity
import io.github.zohrevand.dialogue.core.database.model.NOT_INSERTED_ENTITY_ID

fun Account.asEntity() = AccountEntity(
    id = id ?: NOT_INSERTED_ENTITY_ID,
    username = username,
    domain = domain,
    password = password,
    displayName = displayName,
    resource = resource,
    status = status
)