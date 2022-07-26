package io.github.zohrevand.dialogue.core.data.model

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.dialogue.core.database.model.ConversationEntity

fun Conversation.asEntity() = ConversationEntity(
    peerJid = peerJid
)
