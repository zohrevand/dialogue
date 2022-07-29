package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Conversation
import kotlinx.coroutines.flow.Flow

interface ConversationsRepository {

    fun getConversation(peerJid: String): Flow<Conversation>

    fun getConversationsStream(): Flow<List<Conversation>>

    suspend fun updateConversation(conversation: Conversation)

    suspend fun updateConversations(conversations: List<Conversation>)

    suspend fun deleteConversation(peerJid: String)
}
