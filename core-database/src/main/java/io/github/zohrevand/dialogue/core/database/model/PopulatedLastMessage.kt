package io.github.zohrevand.dialogue.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import io.github.zohrevand.core.model.data.LastMessage

data class PopulatedLastMessage(
    @Embedded
    val entity: LastMessageEntity,
    @Relation(
        parentColumn = "last_message_id",
        entityColumn = "id"
    )
    val lastMessage: MessageEntity
)

fun PopulatedLastMessage.asExternalModel() = LastMessage(
    peerJid = entity.peerJid,
    lastMessage = lastMessage.asExternalModel()
)
