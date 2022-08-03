package io.github.zohrevand.dialogue.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.zohrevand.dialogue.core.database.model.ConversationEntity
import io.github.zohrevand.dialogue.core.database.model.SendingChatStateEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [SendingChatStateEntity] access
 */
@Dao
interface SendingChatStateDao {

    @Query(value = "SELECT * FROM sending_chat_state where consumed = 0")
    fun getSendingChatStateEntitiesStream(): Flow<List<ConversationEntity>>
}
