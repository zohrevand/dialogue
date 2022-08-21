package io.github.zohrevand.core.model.data

import io.github.zohrevand.core.model.data.MessageStatus.Received
import io.github.zohrevand.core.model.data.MessageStatus.ReceivedDisplayed
import io.github.zohrevand.core.model.data.MessageStatus.ShouldSend
import java.util.UUID
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Message(
    val id: Long? = null,
    val stanzaId: String,
    val peerJid: String,
    val body: String,
    val sendTime: Instant = Clock.System.now(),
    val status: MessageStatus
) {
    companion object {
        fun create(text: String, peerJid: String): Message =
            Message(
                stanzaId = UUID.randomUUID().toString(),
                peerJid = peerJid,
                body = text,
                status = ShouldSend
            )
    }
}

val Message.isMine: Boolean
    get() = status != Received && status != ReceivedDisplayed
