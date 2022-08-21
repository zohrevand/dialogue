package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.ChatState
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus
import kotlinx.coroutines.flow.Flow

/**
 * There are multiple update methods to handle various concurrent updates
 * from different parts of application
 */
interface ConversationsRepository {

    fun getConversation(peerJid: String): Flow<Conversation?>

    fun getConversationsStream(status: ConversationStatus): Flow<List<Conversation>>

    suspend fun addConversation(conversation: Conversation): Long

    suspend fun updateConversation(
        peerJid: String,
        unreadMessagesCount: Int,
        chatState: ChatState,
        lastMessageId: Long
    )

    suspend fun updateConversation(
        peerJid: String,
        unreadMessagesCount: Int,
        isChatOpen: Boolean
    )

    suspend fun updateConversation(peerJid: String, lastMessageId: Long)

    suspend fun updateConversation(peerJid: String, chatState: ChatState)

    suspend fun updateConversation(peerJid: String, draftMessage: String?)

    suspend fun updateConversation(peerJid: String, isChatOpen: Boolean)
}
