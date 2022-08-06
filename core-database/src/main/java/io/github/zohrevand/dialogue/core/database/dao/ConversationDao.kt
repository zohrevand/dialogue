package io.github.zohrevand.dialogue.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.zohrevand.core.model.data.ConversationStatus
import io.github.zohrevand.dialogue.core.database.model.ConversationEntity
import io.github.zohrevand.dialogue.core.database.model.PopulatedConversation
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [ConversationEntity] access
 */
@Dao
interface ConversationDao {
    @Query(
        value = """
        SELECT * FROM conversations
        WHERE peer_jid = :peerJid
    """
    )
    fun getConversationEntity(peerJid: String): Flow<PopulatedConversation?>

    @Query(value = "SELECT * FROM conversations")
    fun getConversationEntitiesStream(): Flow<List<PopulatedConversation>>

    /**
     * Get conversations stream based on conversation status
     */
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

    /**
     * Inserts [conversationEntities] into the db if they don't exist, and update those that do
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(conversationEntities: List<ConversationEntity>)

    /**
     * Deletes row in the db matching the specified [peerJid]
     */
    @Query(
        value = """
            DELETE FROM conversations
            WHERE peer_jid = :peerJid
        """
    )
    suspend fun deleteConversation(peerJid: String)
}
