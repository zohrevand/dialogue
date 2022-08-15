package io.github.zohrevand.dialogue.core.data.model

import io.github.zohrevand.core.model.data.LastMessage
import io.github.zohrevand.dialogue.core.database.model.LastMessageEntity

fun LastMessage.asEntity() = LastMessageEntity(
    peerJid = peerJid,
    last_message_id = lastMessage.id ?: throw IllegalStateException("Last message should have id")
)
