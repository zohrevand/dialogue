package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus
import kotlinx.coroutines.flow.Flow

interface ConversationsRepository {

    fun getConversation(peerJid: String): Flow<Conversation>

    fun getConversationsStream(): Flow<List<Conversation>>

    fun getConversationsStream(status: ConversationStatus): Flow<List<Conversation>>

    fun isConversationExists(peerJid: String): Flow<Boolean>

    suspend fun updateConversation(conversation: Conversation)

    suspend fun updateConversations(conversations: List<Conversation>)

    suspend fun deleteConversation(peerJid: String)
}
