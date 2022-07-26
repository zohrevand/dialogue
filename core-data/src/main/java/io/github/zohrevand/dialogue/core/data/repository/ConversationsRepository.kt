package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Conversation
import kotlinx.coroutines.flow.Flow

interface ConversationsRepository {

    fun getConversation(withJid: String): Flow<Conversation>
}
