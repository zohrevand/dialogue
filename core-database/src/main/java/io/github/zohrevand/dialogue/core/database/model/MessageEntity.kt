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
    @ColumnInfo(name = "peer_jid")
    val peerJid: String,
    val body: String,
    @ColumnInfo(name = "send_time")
    val sendTime: Instant,
)

fun MessageEntity.asExternalModel() = Message(
    id = id,
    clientId = clientId,
    serverId = serverId,
    peerJid = peerJid,
    body = body,
    sendTime = sendTime
)
