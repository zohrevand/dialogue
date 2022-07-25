package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.dialogue.core.database.dao.MessageDao
import io.github.zohrevand.dialogue.core.database.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstMessagesRepository @Inject constructor(
    private val messageDao: MessageDao
) : MessagesRepository {

    override fun getMessage(id: String): Flow<Message> =
        messageDao.getMessageEntity(id).map { it.asExternalModel() }
}
