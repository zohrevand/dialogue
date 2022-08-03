package io.github.zohrevand.dialogue.core.data.model

import io.github.zohrevand.core.model.data.SendingChatState
import io.github.zohrevand.dialogue.core.database.model.SendingChatStateEntity

fun SendingChatState.asEntity() = SendingChatStateEntity(
    id = id ?: 0,
    peerJid = peerJid,
    chatState = chatState,
    consumed = consumed
)
