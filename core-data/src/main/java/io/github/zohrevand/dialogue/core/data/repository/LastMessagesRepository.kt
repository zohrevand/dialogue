package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.LastMessage
import kotlinx.coroutines.flow.Flow

interface LastMessagesRepository {

    fun getLastMessagesStream(): Flow<List<LastMessage>>

    suspend fun updateLastMessage(lastMessage: LastMessage)
}
