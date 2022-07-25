package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.dialogue.core.data.model.asEntity
import io.github.zohrevand.dialogue.core.database.dao.MessageDao
import io.github.zohrevand.dialogue.core.database.model.MessageEntity
import io.github.zohrevand.dialogue.core.database.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstMessagesRepository @Inject constructor(
    private val messageDao: MessageDao
) : MessagesRepository {

    override fun getMessage(id: String): Flow<Message> =
        messageDao.getMessageEntity(id).map { it.asExternalModel() }

    override fun getMessagesStream(): Flow<List<Message>> =
        messageDao.getMessageEntitiesStream()
            .map { it.map(MessageEntity::asExternalModel) }

    override fun getMessagesStream(ids: Set<String>): Flow<List<Message>> =
        messageDao.getMessageEntitiesStream(ids)
            .map { it.map(MessageEntity::asExternalModel) }

    override suspend fun updateMessage(message: Message) =
        messageDao.upsert(message.asEntity())

    override suspend fun deleteMessage(id: String) =
        messageDao.deleteMessage(id)
}
