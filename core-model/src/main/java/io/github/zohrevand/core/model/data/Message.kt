package io.github.zohrevand.core.model.data

import io.github.zohrevand.core.model.data.MessageStatus.Sent
import io.github.zohrevand.core.model.data.MessageStatus.SentDelivered
import io.github.zohrevand.core.model.data.MessageStatus.SentDisplayed
import io.github.zohrevand.core.model.data.MessageStatus.SentFailed
import io.github.zohrevand.core.model.data.MessageStatus.ShouldSend
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.UUID

data class Message(
    val id: Long? = null,
    val stanzaId : String,
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
    get() = status == Sent ||
        status == SentFailed ||
        status == SentDelivered ||
        status == SentDisplayed
