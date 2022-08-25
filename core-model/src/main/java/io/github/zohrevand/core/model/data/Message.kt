package io.github.zohrevand.core.model.data

import io.github.zohrevand.core.model.data.MessageStatus.Received
import io.github.zohrevand.core.model.data.MessageStatus.ReceivedDisplayed
import io.github.zohrevand.core.model.data.MessageStatus.ShouldSend
import java.util.UUID
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class Message(
    val id: Long? = null,
    val stanzaId: String,
    val peerJid: String,
    val body: String,
    val sendTime: Instant = Clock.System.now(),
    val status: MessageStatus
) {
    // This message is sent by logged-in account
    val isMine: Boolean
        get() = status != Received && status != ReceivedDisplayed

    fun withStatus(status: MessageStatus) = Message(
        id = this.id,
        stanzaId = this.stanzaId,
        peerJid = this.peerJid,
        body = this.body,
        sendTime = this.sendTime,
        status = status
    )

    companion object {
        fun createNewMessage(text: String, peerJid: String): Message =
            Message(
                stanzaId = UUID.randomUUID().toString(),
                peerJid = peerJid,
                body = text,
                status = ShouldSend
            )

        fun createReceivedMessage(stanzaId: String, text: String, peerJid: String): Message =
            Message(
                stanzaId = stanzaId,
                peerJid = peerJid,
                body = text,
                status = Received
            )
    }
}
