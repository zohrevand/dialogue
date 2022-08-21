package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.core.model.data.MessageStatus
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {

    fun getMessage(id: String): Flow<Message>

    fun getMessageByStanzaId(stanzaId: String): Flow<Message?>

    fun getMessagesStream(): Flow<List<Message>>

    fun getMessagesStream(peerJid: String): Flow<List<Message>>

    fun getMessagesStream(ids: Set<String>): Flow<List<Message>>

    fun getMessagesStream(status: MessageStatus): Flow<List<Message>>

    suspend fun addMessage(message: Message): Long

    suspend fun updateMessage(message: Message)

    suspend fun updateMessages(messages: List<Message>)

    suspend fun deleteMessage(id: String)

    suspend fun handleIncomingMessage(message: Message, maybeNewConversation: Conversation)
}
