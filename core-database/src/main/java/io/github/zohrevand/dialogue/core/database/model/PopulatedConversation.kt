package io.github.zohrevand.dialogue.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import io.github.zohrevand.core.model.data.Conversation

/**
 * External data layer representation of a fully populated conversation
 */
data class PopulatedConversation(
    @Embedded
    val entity: ConversationEntity,
    @Relation(
        parentColumn = "last_message_id",
        entityColumn = "id"
    )
    val lastMessage: MessageEntity?
)

fun PopulatedConversation.asExternalModel() = Conversation(
    peerJid = entity.peerJid,
    status = entity.status,
    draftMessage = entity.draftMessage,
    lastMessage = lastMessage?.asExternalModel(),
    unreadMessagesCount = entity.unreadMessagesCount,
    chatState = entity.chatState,
    isChatOpen = entity.isChatOpen
)
