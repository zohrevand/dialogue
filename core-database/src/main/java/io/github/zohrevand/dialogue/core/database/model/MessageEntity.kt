package io.github.zohrevand.dialogue.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.core.model.data.MessageStatus
import kotlinx.datetime.Instant

@Entity(
    tableName = "messages",
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "client_id")
    val clientId: String,
    @ColumnInfo(name = "peer_jid")
    val peerJid: String,
    val body: String,
    @ColumnInfo(name = "send_time")
    val sendTime: Instant,
    val status: MessageStatus
)

fun MessageEntity.asExternalModel() = Message(
    id = id,
    clientId = clientId,
    peerJid = peerJid,
    body = body,
    sendTime = sendTime,
    status = status
)
