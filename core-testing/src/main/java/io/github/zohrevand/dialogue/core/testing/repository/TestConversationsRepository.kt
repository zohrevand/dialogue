package io.github.zohrevand.dialogue.core.testing.repository

import io.github.zohrevand.core.model.data.ChatState
import io.github.zohrevand.core.model.data.Conversation
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

    override fun getConversationsStream(): Flow<List<Conversation>> = conversationsFlow

    override suspend fun addConversation(conversation: Conversation): Long {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun updateConversation(
        peerJid: String,
        unreadMessagesCount: Int,
        chatState: ChatState,
        lastMessageId: Long
    ) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun updateConversation(
        peerJid: String,
        unreadMessagesCount: Int,
        isChatOpen: Boolean
    ) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun updateConversation(peerJid: String, lastMessageId: Long) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun updateConversation(peerJid: String, chatState: ChatState) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun updateConversation(peerJid: String, draftMessage: String?) {
        throw NotImplementedError("Unused in tests")
    }

    override suspend fun updateConversation(peerJid: String, isChatOpen: Boolean) {
        throw NotImplementedError("Unused in tests")
    }

    /**
     * A test-only API to allow controlling the list of conversations from tests.
     */
    fun sendConversations(conversations: List<Conversation>) {
        conversationsFlow.tryEmit(conversations)
    }
}
