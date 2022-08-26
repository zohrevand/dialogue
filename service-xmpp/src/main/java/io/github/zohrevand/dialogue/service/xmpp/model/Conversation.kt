package io.github.zohrevand.dialogue.service.xmpp.model

import io.github.zohrevand.core.model.data.Conversation
import org.jxmpp.jid.EntityBareJid

fun EntityBareJid.asConversation() = Conversation(
    peerJid = asBareJid().toString(),
)
