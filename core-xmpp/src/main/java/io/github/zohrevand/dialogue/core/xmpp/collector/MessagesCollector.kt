package io.github.zohrevand.dialogue.core.xmpp.collector

import io.github.zohrevand.core.model.data.Message

interface MessagesCollector {
    /**
     * Collects the changes to messages stream which should be send
     * by XMPP, originated from database
     * */
    suspend fun collectShouldSendMessages(
        sendMessage: suspend (List<Message>) -> Unit
    )
}
