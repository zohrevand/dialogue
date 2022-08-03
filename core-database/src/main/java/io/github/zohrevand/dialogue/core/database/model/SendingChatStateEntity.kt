package io.github.zohrevand.dialogue.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.zohrevand.core.model.data.ChatState

@Entity(
    tableName = "sending_chat_state",
)
data class SendingChatStateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "chat_state")
    val chatState: ChatState,
    val consumed: Boolean
)
