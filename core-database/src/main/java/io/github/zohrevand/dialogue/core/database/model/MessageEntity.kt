package io.github.zohrevand.dialogue.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(
    tableName = "messages",
)
data class MessageEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "to_jid")
    val toJid: String,
    @ColumnInfo(name = "from_jid")
    val fromJid: String,
    val body: String,
    @ColumnInfo(name = "message_time")
    val time: Instant,
)
