package io.github.zohrevand.dialogue.core.database.dao

import androidx.room.Dao
import androidx.room.Query
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
}
