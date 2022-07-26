package io.github.zohrevand.core.model.data

import kotlinx.datetime.Instant

data class Message(
    val id: String,
    val clientId : String?,
    val serverId: String?,
    val peerJid: String,
    val body: String,
    val sendTime: Instant,
    val status: MessageStatus
)
