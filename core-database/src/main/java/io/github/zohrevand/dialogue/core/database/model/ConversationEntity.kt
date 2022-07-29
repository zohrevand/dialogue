package io.github.zohrevand.dialogue.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus

@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey
    @ColumnInfo(name = "peer_jid")
    val peerJid: String,
    val status: ConversationStatus,
    @ColumnInfo(name = "draft_message")
    val draftMessage: String?
)

fun ConversationEntity.asExternalModel() = Conversation(
    peerJid = peerJid,
    status = status,
    draftMessage = draftMessage
)
