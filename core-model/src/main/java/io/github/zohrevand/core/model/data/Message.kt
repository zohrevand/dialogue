package io.github.zohrevand.core.model.data

import kotlinx.datetime.Instant

data class Message(
    val id: String? = null,
    val clientId : String? = null,
    val serverId: String? = null,
    val peerJid: String,
    val body: String,
    val sendTime: Instant,
    val status: MessageStatus
)
