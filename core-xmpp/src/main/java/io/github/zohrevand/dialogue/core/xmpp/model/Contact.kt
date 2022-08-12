package io.github.zohrevand.dialogue.core.xmpp.model

import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.core.model.data.Presence
import kotlinx.datetime.Clock
import org.jivesoftware.smack.roster.RosterEntry

fun RosterEntry.asExternalModel() = Contact(
    jid = jid.asBareJid().toString(),
    presence = Presence(),
    lastTime = Clock.System.now(),
    shouldAddToRoster = false
)
