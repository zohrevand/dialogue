package io.github.zohrevand.core.model.data

import io.github.zohrevand.core.model.data.MessageStatus.Received
import io.github.zohrevand.core.model.data.MessageStatus.ReceivedDisplayed
import io.github.zohrevand.core.model.data.MessageStatus.ShouldSend
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toLocalDateTime

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

val Message.sendTimeFormatted: String
    get() {
        val zoneId = ZoneId.systemDefault()
        val timeZone = TimeZone.currentSystemDefault()
        val today = Clock.System.now().toLocalDateTime(timeZone).date
        val yesterday = today.minus(1, DateTimeUnit.DAY)
        val sendTimeLocalDateTime = sendTime.toLocalDateTime(timeZone)
        val sendTimeLocalDate = sendTimeLocalDateTime.date

        if (today == sendTimeLocalDate) {
            return "${sendTimeLocalDateTime.hour}:${sendTimeLocalDateTime.minute}"
        } else if (yesterday == sendTimeLocalDate) {
            return "Yesterday"
        }

        return DateTimeFormatter.ofPattern("M/d/yyyy")
            .withZone(zoneId).format(sendTime.toJavaInstant())
    }

val Message.sendTimeLocalDate: String
    get() {
        val zoneId = ZoneId.systemDefault()
        val timeZone = TimeZone.currentSystemDefault()
        val today = Clock.System.now().toLocalDateTime(timeZone).date
        val yesterday = today.minus(1, DateTimeUnit.DAY)
        val sendTimeLocalDateTime = sendTime.toLocalDateTime(timeZone)
        val sendTimeLocalDate = sendTimeLocalDateTime.date

        if (today == sendTimeLocalDate) {
            return "Today"
        } else if (yesterday == sendTimeLocalDate) {
            return "Yesterday"
        }

        return DateTimeFormatter.ofPattern("M/d/yyyy")
            .withZone(zoneId).format(sendTime.toJavaInstant())
    }
