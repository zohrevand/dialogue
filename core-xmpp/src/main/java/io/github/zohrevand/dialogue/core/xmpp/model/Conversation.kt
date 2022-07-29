package io.github.zohrevand.dialogue.core.xmpp.model

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus.NotStarted
import org.jivesoftware.smack.roster.RosterEntry

fun RosterEntry.asConversation() = Conversation(
    peerJid = jid.asBareJid().toString(),
    status = NotStarted
)
