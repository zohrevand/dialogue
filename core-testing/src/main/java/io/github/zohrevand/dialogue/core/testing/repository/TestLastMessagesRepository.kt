package io.github.zohrevand.dialogue.core.testing.repository

import io.github.zohrevand.core.model.data.LastMessage
import io.github.zohrevand.dialogue.core.data.repository.LastMessagesRepository
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestLastMessagesRepository : LastMessagesRepository {

    /**
     * The backing hot flow for the list of last messages for testing
     */
    private val lastMessagesFlow: MutableSharedFlow<List<LastMessage>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = DROP_OLDEST)

    override fun getLastMessagesStream(): Flow<List<LastMessage>> = lastMessagesFlow

    override suspend fun updateLastMessage(lastMessage: LastMessage) {
        lastMessagesFlow.tryEmit(listOf(lastMessage))
    }
}
