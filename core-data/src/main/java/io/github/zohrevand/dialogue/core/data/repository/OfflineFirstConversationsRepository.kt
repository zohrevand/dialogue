package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.ChatState
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

    override suspend fun addConversation(conversation: Conversation): Long =
        conversationDao.insert(conversation.asEntity())

    override suspend fun updateConversation(
        peerJid: String,
        unreadMessagesCount: Int,
        chatState: ChatState,
        lastMessageId: Long
    ) = conversationDao.update(
        peerJid = peerJid,
        unreadMessagesCount = unreadMessagesCount,
        chatState = chatState,
        lastMessageId = lastMessageId
    )

    override suspend fun updateConversation(
        peerJid: String,
        unreadMessagesCount: Int,
        isChatOpen: Boolean
    ) = conversationDao.update(
        peerJid = peerJid,
        unreadMessagesCount = unreadMessagesCount,
        isChatOpen = isChatOpen
    )

    override suspend fun updateConversation(peerJid: String, lastMessageId: Long) =
        conversationDao.update(peerJid = peerJid, lastMessageId = lastMessageId)

    override suspend fun updateConversation(peerJid: String, chatState: ChatState) =
        conversationDao.update(peerJid = peerJid, chatState = chatState)
}
