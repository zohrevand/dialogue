package io.github.zohrevand.dialogue.core.testing.repository

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import kotlinx.coroutines.flow.Flow

class TestConversationsRepository : ConversationsRepository {

    override fun getConversation(peerJid: String): Flow<Conversation?> {
        TODO("Not yet implemented")
    }

    override fun getConversationsStream(): Flow<List<Conversation>> {
        TODO("Not yet implemented")
    }

    override fun getConversationsStream(status: ConversationStatus): Flow<List<Conversation>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateConversation(conversation: Conversation) {
        TODO("Not yet implemented")
    }

    override suspend fun updateConversations(conversations: List<Conversation>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteConversation(peerJid: String) {
        TODO("Not yet implemented")
    }
}