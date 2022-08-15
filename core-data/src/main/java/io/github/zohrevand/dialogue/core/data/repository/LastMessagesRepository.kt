package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.LastMessage
import io.github.zohrevand.dialogue.core.database.model.PopulatedLastMessage
import kotlinx.coroutines.flow.Flow

interface LastMessagesRepository {

    fun getLastMessagesStream(): Flow<List<PopulatedLastMessage>>

    suspend fun updateLastMessage(lastMessage: LastMessage)
}
