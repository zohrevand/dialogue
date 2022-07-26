package io.github.zohrevand.core.model.data

import kotlinx.datetime.Instant

data class Message(
    val id: String,
    val clientId : String?,
    val serverId: String?,
    val fromJid: String,
    val toJid: String,
    val body: String,
    val sendTime: Instant
)
