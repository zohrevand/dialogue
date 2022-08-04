package io.github.zohrevand.dialogue.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.zohrevand.core.model.data.ChatState
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus

@Entity(
    tableName = "conversations",
    foreignKeys = [
        ForeignKey(
            entity = MessageEntity::class,
            parentColumns = ["id"],
            childColumns = ["last_message_id"],
            onDelete = ForeignKey.NO_ACTION
        ),
    ]
)
data class ConversationEntity(
    @PrimaryKey
    @ColumnInfo(name = "peer_jid")
    val peerJid: String,
    val status: ConversationStatus,
    @ColumnInfo(name = "draft_message")
    val draftMessage: String?,
    @ColumnInfo(name = "last_message_id")
    val lastMessageId: Long?,
    @ColumnInfo(name = "unread_messages_count")
    val unreadMessagesCount: Int,
    @ColumnInfo(name = "chat_state")
    val chatState: ChatState
)

fun ConversationEntity.asExternalModel() = Conversation(
    peerJid = peerJid,
    status = status,
    draftMessage = draftMessage,
    chatState = chatState
)
