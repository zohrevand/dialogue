package io.github.zohrevand.dialogue.service.xmpp.collector

import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.core.model.data.MessageStatus
import io.github.zohrevand.core.model.data.MessageStatus.Sending
import io.github.zohrevand.dialogue.core.data.repository.MessagesRepository
import javax.inject.Inject

class MessagesCollectorImpl @Inject constructor(
    private val messagesRepository: MessagesRepository
) : MessagesCollector {

    override suspend fun collectShouldSendMessages(sendMessages: suspend (List<Message>) -> Unit) {
        messagesRepository.getMessagesStream(status = MessageStatus.ShouldSend)
            .collect { messages ->
                val updatedMessages = messages.map { it.withStatus(status = Sending) }
                messagesRepository.updateMessages(updatedMessages)
                sendMessages(updatedMessages)
            }
    }
}
