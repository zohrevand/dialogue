package io.github.zohrevand.dialogue.core.xmpp.model

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus.Started
import org.jxmpp.jid.EntityBareJid

fun EntityBareJid.asConversation() = Conversation(
    peerJid = asBareJid().toString(),
    status = Started
)
