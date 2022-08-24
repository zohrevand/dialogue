package io.github.zohrevand.dialogue.service.xmpp.model

import io.github.zohrevand.core.model.data.Message
import org.jivesoftware.smack.packet.Message as SmackMessage

fun SmackMessage.asExternalModel() = Message.createReceivedMessage(
    stanzaId = stanzaId,
    peerJid = from.asBareJid().toString(),
    text = body
)
