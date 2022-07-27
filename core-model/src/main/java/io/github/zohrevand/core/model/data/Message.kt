package io.github.zohrevand.core.model.data

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Message(
    val id: Long? = null,
    val clientId : String? = null,
    val serverId: String? = null,
    val peerJid: String,
    val body: String,
    val sendTime: Instant = Clock.System.now(),
    val status: MessageStatus
)
