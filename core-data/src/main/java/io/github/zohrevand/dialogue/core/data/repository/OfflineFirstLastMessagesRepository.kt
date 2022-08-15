package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.LastMessage
import io.github.zohrevand.dialogue.core.database.dao.LastMessageDao
import io.github.zohrevand.dialogue.core.database.model.PopulatedLastMessage
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class OfflineFirstLastMessagesRepository @Inject constructor(
    private val lastMessageDao: LastMessageDao
) : LastMessagesRepository {
    override fun getLastMessagesStream(): Flow<List<PopulatedLastMessage>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateLastMessage(lastMessage: LastMessage) {
        TODO("Not yet implemented")
    }
}