package io.github.zohrevand.core.model.data

import io.github.zohrevand.core.model.data.MessageStatus.ShouldSend
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.UUID

data class Message(
    val id: Long? = null,
    val clientId : String,
    val serverId: String? = null,
    val peerJid: String,
    val body: String,
    val sendTime: Instant = Clock.System.now(),
    val status: MessageStatus
) {
    companion object {
        fun create(text: String, peerJid: String): Message =
            Message(
                clientId = UUID.randomUUID().toString(),
                peerJid = peerJid,
                body = text,
                status = ShouldSend
            )
    }
}
