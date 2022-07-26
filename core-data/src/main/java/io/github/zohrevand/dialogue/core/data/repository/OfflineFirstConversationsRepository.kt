package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.dialogue.core.data.model.asEntity
import io.github.zohrevand.dialogue.core.database.dao.ConversationDao
import io.github.zohrevand.dialogue.core.database.model.ConversationEntity
import io.github.zohrevand.dialogue.core.database.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstConversationsRepository @Inject constructor(
    private val conversationDao: ConversationDao
) : ConversationsRepository {

    override fun getConversation(peerJid: String): Flow<Conversation> =
        conversationDao.getConversationEntity(peerJid).map(ConversationEntity::asExternalModel)

    override fun getConversationsStream(): Flow<List<Conversation>> =
        conversationDao.getConversationEntitiesStream()
            .map { it.map(ConversationEntity::asExternalModel) }

    override suspend fun updateConversation(conversation: Conversation) =
        conversationDao.upsert(conversation.asEntity())

    override suspend fun deleteConversation(peerJid: String) =
        conversationDao.deleteConversation(peerJid)
}
