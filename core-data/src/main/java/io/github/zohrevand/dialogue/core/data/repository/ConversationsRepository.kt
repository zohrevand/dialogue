package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus
import kotlinx.coroutines.flow.Flow

interface ConversationsRepository {

    fun getConversation(peerJid: String): Flow<Conversation?>

    fun getConversationsStream(status: ConversationStatus): Flow<List<Conversation>>

    suspend fun updateConversation(conversation: Conversation)
}
