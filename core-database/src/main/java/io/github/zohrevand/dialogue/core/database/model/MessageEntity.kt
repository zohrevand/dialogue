package io.github.zohrevand.dialogue.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.zohrevand.core.model.data.Message
import kotlinx.datetime.Instant

@Entity(
    tableName = "messages",
)
data class MessageEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "client_id")
    val clientId: String?,
    @ColumnInfo(name = "server_id")
    val serverId: String?,
    @ColumnInfo(name = "from_jid")
    val fromJid: String,
    @ColumnInfo(name = "to_jid")
    val toJid: String,
    val body: String,
    @ColumnInfo(name = "send_time")
    val sendTime: Instant,
)

fun MessageEntity.asExternalModel() = Message(
    id = id,
    clientId = clientId,
    serverId = serverId,
    fromJid = fromJid,
    toJid = toJid,
    body = body,
    sendTime = sendTime
)
