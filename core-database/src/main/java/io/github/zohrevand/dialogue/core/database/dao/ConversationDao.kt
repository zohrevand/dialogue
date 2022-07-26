package io.github.zohrevand.dialogue.core.database.dao

import androidx.room.Dao
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
}
