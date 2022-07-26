package io.github.zohrevand.dialogue.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.zohrevand.dialogue.core.database.model.ConversationEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [ConversationEntity] access
 */
@Dao
interface ConversationDao {
    @Query(
        value = """
        SELECT * FROM conversations
        WHERE with_jid = :withJid
    """
    )
    fun getConversationEntity(withJid: String): Flow<ConversationEntity>

    @Query(value = "SELECT * FROM conversations")
    fun getConversationEntitiesStream(): Flow<List<ConversationEntity>>

    /**
     * Inserts [conversationEntity] into the db if it doesn't exist, and update if it do
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(conversationEntity: ConversationEntity)

    /**
     * Deletes row in the db matching the specified [withJid]
     */
    @Query(
        value = """
            DELETE FROM conversations
            WHERE with_jid = :withJid
        """
    )
    suspend fun deleteConversation(withJid: String)
}
