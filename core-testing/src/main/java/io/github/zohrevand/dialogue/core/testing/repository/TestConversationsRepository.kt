package io.github.zohrevand.dialogue.core.testing.repository

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class TestConversationsRepository : ConversationsRepository {

    /**
     * The backing hot flow for the list of conversations for testing
     */
    private val conversationsFlow: MutableSharedFlow<List<Conversation>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = DROP_OLDEST)

    override fun getConversation(peerJid: String): Flow<Conversation?> =
        conversationsFlow.map { conversations -> conversations.find { it.peerJid == peerJid } }

    override fun getConversationsStream(status: ConversationStatus): Flow<List<Conversation>> =
        conversationsFlow.map { conversations -> conversations.filter { it.status == status } }

    override suspend fun updateConversation(conversation: Conversation) {
        TODO("Not yet implemented")
    }
}