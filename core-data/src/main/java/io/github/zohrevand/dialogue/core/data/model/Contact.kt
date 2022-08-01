package io.github.zohrevand.dialogue.core.data.model

import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.core.database.model.ContactEntity

fun Contact.asEntity() = ContactEntity(
    jid = jid,
    presenceType = presence.type,
    presenceMode = presence.mode,
    presenceStatus = presence.status,
    presencePriority = presence.priority,
    lastTime = lastTime,
    shouldAddToRoster = shouldAddToRoster
)
