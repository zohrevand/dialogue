package io.github.zohrevand.core.model.data

import kotlinx.datetime.Instant

data class Message(
    val id: String,
    val fromJid: String,
    val toJid: String,
    val body: String,
    val sendTime: Instant
)
