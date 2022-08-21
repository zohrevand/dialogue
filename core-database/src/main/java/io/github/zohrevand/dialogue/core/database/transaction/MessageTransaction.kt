package io.github.zohrevand.dialogue.core.database.transaction

import io.github.zohrevand.dialogue.core.database.model.ConversationEntity
import io.github.zohrevand.dialogue.core.database.model.MessageEntity

interface MessageTransaction {

    suspend fun handleIncomingMessage(
        messageEntity: MessageEntity,
        maybeNewConversationEntity: ConversationEntity
    )
}
