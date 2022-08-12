package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus
import io.github.zohrevand.dialogue.core.data.model.asEntity
import io.github.zohrevand.dialogue.core.database.dao.ConversationDao
import io.github.zohrevand.dialogue.core.database.model.PopulatedConversation
import io.github.zohrevand.dialogue.core.database.model.asExternalModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstConversationsRepository @Inject constructor(
    private val conversationDao: ConversationDao
) : ConversationsRepository {

    override fun getConversation(peerJid: String): Flow<Conversation?> =
        conversationDao.getConversationEntity(peerJid).map { it?.asExternalModel() }

    override fun getConversationsStream(status: ConversationStatus): Flow<List<Conversation>> =
        conversationDao.getConversationEntitiesStream(status)
            .map { it.map(PopulatedConversation::asExternalModel) }

    override suspend fun updateConversation(conversation: Conversation) =
        conversationDao.upsert(conversation.asEntity())
}
