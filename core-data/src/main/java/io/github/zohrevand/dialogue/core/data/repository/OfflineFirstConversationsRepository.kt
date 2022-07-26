package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.dialogue.core.database.dao.ConversationDao
import javax.inject.Inject

class OfflineFirstConversationsRepository @Inject constructor(
    private val conversationDao: ConversationDao
) : ConversationsRepository {

}
