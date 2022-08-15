package io.github.zohrevand.dialogue.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.github.zohrevand.dialogue.core.database.model.LastMessageEntity
import io.github.zohrevand.dialogue.core.database.model.PopulatedLastMessage
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [LastMessageEntity] access
 */
@Dao
interface LastMessageDao {

    @Transaction
    @Query(value = "SELECT * FROM last_messages")
    fun getLastMessagesStream(): Flow<List<PopulatedLastMessage>>

    /**
     * Inserts [lastMessageEntity] into the db if it doesn't exist, and update if it do
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(lastMessageEntity: LastMessageEntity)
}
