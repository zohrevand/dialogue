package io.github.zohrevand.dialogue.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.zohrevand.dialogue.core.database.model.ContactEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [ContactEntity] access
 */
@Dao
interface ContactDao {
    @Query(
        value = """
        SELECT * FROM contacts
        WHERE jid = :jid
    """
    )
    fun getContactEntity(jid: String): Flow<ContactEntity>
}