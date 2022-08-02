package io.github.zohrevand.dialogue.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.zohrevand.core.model.data.MessageStatus
import io.github.zohrevand.dialogue.core.database.model.MessageEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [MessageEntity] access
 */
@Dao
interface MessageDao {
    @Query(
        value = """
        SELECT * FROM messages
        WHERE id = :id
    """
    )
    fun getMessageEntity(id: String): Flow<MessageEntity>

    @Query(value = "SELECT * FROM messages")
    fun getMessageEntitiesStream(): Flow<List<MessageEntity>>

    /**
     * Get messages stream based on peerJid
     */
    @Query(
        value = """
        SELECT * FROM messages
        WHERE peer_jid = :peerJid
        ORDER BY send_time DESC
    """
    )
    fun getMessageEntitiesStream(peerJid: String): Flow<List<MessageEntity>>

    /**
     * Get messages stream based on message status
     */
    @Query(
        value = """
        SELECT * FROM messages
        WHERE status == :status
    """
    )
    fun getMessageEntitiesStream(status: MessageStatus): Flow<List<MessageEntity>>

    @Query(
        value = """
        SELECT * FROM messages
        WHERE id IN (:ids)
    """
    )
    fun getMessageEntitiesStream(ids: Set<String>): Flow<List<MessageEntity>>

    /**
     * Inserts [messageEntity] into the db if it doesn't exist, and update if it do
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(messageEntity: MessageEntity)

    /**
     * Inserts [messageEntities] into the db if they don't exist, and update those that do
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(messageEntities: List<MessageEntity>)

    /**
     * Deletes row in the db matching the specified [id]
     */
    @Query(
        value = """
            DELETE FROM messages
            WHERE id = :id
        """
    )
    suspend fun deleteMessage(id: String)
}
