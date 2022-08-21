package io.github.zohrevand.dialogue.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.github.zohrevand.core.model.data.ChatState
import io.github.zohrevand.core.model.data.ConversationStatus
import io.github.zohrevand.dialogue.core.database.model.ConversationEntity
import io.github.zohrevand.dialogue.core.database.model.PopulatedConversation
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [ConversationEntity] access
 */
@Dao
interface ConversationDao {
    @Transaction
    @Query(
        value = """
        SELECT * FROM conversations
        WHERE peer_jid = :peerJid
    """
    )
    fun getConversationEntity(peerJid: String): Flow<PopulatedConversation?>

    /**
     * Get conversations stream based on conversation status
     */
    @Transaction
    @Query(
        value = """
        SELECT * FROM conversations
        WHERE status == :status
    """
    )
    fun getConversationEntitiesStream(status: ConversationStatus): Flow<List<PopulatedConversation>>

    /**
     * Inserts [conversationEntity] into the db if it doesn't exist, and update if it do
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(conversationEntity: ConversationEntity)

    @Query(
        """
            UPDATE conversations
            SET
            unread_messages_count = :unreadMessagesCount,
            chat_state = :chatState,
            last_message_id = :lastMessageId
            WHERE peer_jid = :peerJid
        """
    )
    suspend fun update(
        peerJid: String,
        unreadMessagesCount: Int,
        chatState: ChatState,
        lastMessageId: Long
    )
}
