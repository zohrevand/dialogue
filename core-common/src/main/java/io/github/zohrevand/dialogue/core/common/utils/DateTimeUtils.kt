package io.github.zohrevand.dialogue.core.common.utils

import java.time.ZoneId
import java.time.format.DateTimeFormatter
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

val Instant.formatted: String
    get() = when (this.localDate) {
        today -> localTime
        yesterday -> "Yesterday"
        else -> dateFormatted
    }

val LocalDate.formatted: String
    get() = when (this) {
        today -> "Today"
        yesterday -> "Yesterday"
        else -> dateFormatted
    }

val Instant.localTime: String
    get() = "${localDateTime.hour.twoDigit}:${localDateTime.minute.twoDigit}"

val Instant.localDate: LocalDate
    get() = localDateTime.date

private val Instant.localDateTime: LocalDateTime
    get() = toLocalDateTime(TimeZone.currentSystemDefault())

private val Instant.dateFormatted: String
    get() = dateTimeFormatter.withZone(ZoneId.systemDefault()).format(this.toJavaInstant())

private val LocalDate.dateFormatted: String
    get() = toJavaLocalDate().format(dateTimeFormatter)

private val today: LocalDate
    get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

private val yesterday: LocalDate
    get() = today.minus(1, DateTimeUnit.DAY)

private val dateTimeFormatter: DateTimeFormatter
    get() = DateTimeFormatter.ofPattern("M/d/yyyy")

private val Int.twoDigit
    get() = if (this < 10) "0$this" else "$this"
