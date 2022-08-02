package io.github.zohrevand.dialogue.core.xmpp.model

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus.NotStarted
import io.github.zohrevand.core.model.data.ConversationStatus.Started
import org.jivesoftware.smack.roster.RosterEntry
import org.jxmpp.jid.EntityBareJid

fun RosterEntry.asConversation() = Conversation(
    peerJid = jid.asBareJid().toString(),
    status = NotStarted
)

fun EntityBareJid.asConversation() = Conversation(
    peerJid = asBareJid().toString(),
    status = Started
)
