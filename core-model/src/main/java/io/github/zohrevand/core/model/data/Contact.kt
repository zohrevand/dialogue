package io.github.zohrevand.core.model.data

import kotlinx.datetime.Instant

data class Contact(
    val jid: String,
    val presence: Presence,
    val lastTime: Instant
)
