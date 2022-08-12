package io.github.zohrevand.dialogue.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.zohrevand.core.model.data.ChatState
import io.github.zohrevand.core.model.data.SendingChatState

@Entity(
    tableName = "sending_chat_state",
)
data class SendingChatStateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "peer_jid")
    val peerJid: String,
    @ColumnInfo(name = "chat_state")
    val chatState: ChatState,
    val consumed: Boolean
)

fun SendingChatStateEntity.asExternalModel() = SendingChatState(
    id = id,
    peerJid = peerJid,
    chatState = chatState,
    consumed = consumed
)
