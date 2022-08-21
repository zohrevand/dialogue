package io.github.zohrevand.dialogue.core.database.transaction

import androidx.room.withTransaction
import io.github.zohrevand.core.model.data.ChatState
import io.github.zohrevand.dialogue.core.database.DialogueDatabase
import io.github.zohrevand.dialogue.core.database.model.ConversationEntity
import io.github.zohrevand.dialogue.core.database.model.MessageEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MessageTransactionImpl @Inject constructor(
    private val database: DialogueDatabase
) : MessageTransaction {

    private val mutex = Mutex()

    override suspend fun handleIncomingMessage(
        messageEntity: MessageEntity,
        maybeNewConversationEntity: ConversationEntity
    ) = mutex.withLock {
        database.withTransaction {
            val conversationDao = database.conversationDao()
            val messageDao = database.messageDao()

            val peerJid = messageEntity.peerJid
            val conversation = conversationDao.getConversationEntity(peerJid).first()

            if (conversation == null) {
                conversationDao.insert(maybeNewConversationEntity)
            }

            val messageId = messageDao.insert(messageEntity)

            val unreadMessagesCount =
                if (conversation == null) 1
                else if (conversation.entity.isChatOpen) 0
                else conversation.entity.unreadMessagesCount + 1

            conversationDao.update(
                peerJid = peerJid,
                unreadMessagesCount = unreadMessagesCount,
                chatState = ChatState.Active,
                lastMessageId = messageId
            )
        }
    }
}
