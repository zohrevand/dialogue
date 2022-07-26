package io.github.zohrevand.dialogue.core.data.model

import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.dialogue.core.database.model.MessageEntity

fun Message.asEntity() = MessageEntity(
    id = id,
    clientId = clientId,
    serverId = serverId,
    fromJid = fromJid,
    toJid = toJid,
    body = body,
    sendTime = sendTime
)
