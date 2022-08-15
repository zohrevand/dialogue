package io.github.zohrevand.dialogue.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import io.github.zohrevand.core.model.data.Message

data class PopulatedLastMessage(
    @Embedded
    val entity: LastMessageEntity,
    @Relation(
        parentColumn = "last_message_id",
        entityColumn = "id"
    )
    val lastMessage: Message
)
