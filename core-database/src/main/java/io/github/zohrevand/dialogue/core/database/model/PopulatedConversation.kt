package io.github.zohrevand.dialogue.core.database.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * External data layer representation of a fully populated conversation
 */
data class PopulatedConversation(
    @Embedded
    val entity: ConversationEntity,
    @Relation(
        parentColumn = "episode_id",
        entityColumn = "id"
    )
    val lastMessage: MessageEntity
)