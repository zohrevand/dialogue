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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaLocalDate
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
    get() = if (sendTime.isToday) {
        sendTime.localTime
    } else if (sendTime.isYesterday) {
        "Yesterday"
    } else {
        sendTime.dateFormatted
    }

val LocalDate.formatted: String
    get() = if (this == today) {
        "Today"
    } else if (this == yesterday) {
        "Yesterday"
    } else {
        dateFormatted
    }

private val today: LocalDate
    get() = Clock.System.now().toLocalDateTime(currentSystemTimeZone).date

private val currentSystemTimeZone: TimeZone
    get() = TimeZone.currentSystemDefault()

private val yesterday: LocalDate
    get() = today.minus(1, DateTimeUnit.DAY)

private val Instant.isToday: Boolean
    get() = localDate == today

private val Instant.isYesterday: Boolean
    get() = localDate == yesterday

val Instant.localTime: String
    get() = "${localDateTime.hour}:${localDateTime.minute}"

val Instant.localDate: LocalDate
    get() = localDateTime.date

private val Instant.localDateTime: LocalDateTime
    get() = toLocalDateTime(TimeZone.currentSystemDefault())

private val Instant.dateFormatted: String
    get() = DateTimeFormatter
        .ofPattern("M/d/yyyy")
        .withZone(ZoneId.systemDefault())
        .format(this.toJavaInstant())

private val LocalDate.dateFormatted: String
    get() = toJavaLocalDate().format(DateTimeFormatter.ofPattern("M/d/yyyy"))
