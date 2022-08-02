package io.github.zohrevand.dialogue.core.xmpp.model

import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.core.model.data.MessageStatus.Received
import kotlinx.datetime.Clock
import org.jivesoftware.smack.packet.Message as SmackMessage

fun SmackMessage.asExternalModel() = Message(
    stanzaId = stanzaId,
    peerJid = from.asBareJid().toString(),
    body = body,
    // TODO: sentTime should be handled differently for offline messages
    sendTime = Clock.System.now(),
    status = Received
)