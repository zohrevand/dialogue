package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.dialogue.core.database.dao.MessageDao
import javax.inject.Inject

class OfflineFirstMessagesRepository @Inject constructor(
    private val messageDao: MessageDao
) : MessagesRepository {

}
