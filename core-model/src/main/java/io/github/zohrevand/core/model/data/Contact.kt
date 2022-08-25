package io.github.zohrevand.core.model.data

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Contact(
    val jid: String,
    val presence: Presence,
    val lastTime: Instant,
    /**
     * To flag the contact to be added to roster entries
     */
    val shouldAddToRoster: Boolean
) {
    val firstLetter: String
        get() = jid.take(1).uppercase()

    companion object {
        fun create(jid: String): Contact =
            Contact(
                jid = jid,
                presence = Presence(),
                lastTime = Clock.System.now(),
                shouldAddToRoster = true
            )
    }
}
