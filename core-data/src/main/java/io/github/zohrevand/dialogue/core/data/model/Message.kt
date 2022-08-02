package io.github.zohrevand.dialogue.core.data.model

import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.dialogue.core.database.model.MessageEntity

fun Message.asEntity() = MessageEntity(
    id = id ?: 0,
    stanzaId = stanzaId,
    peerJid = peerJid,
    body = body,
    sendTime = sendTime,
    status = status
)
