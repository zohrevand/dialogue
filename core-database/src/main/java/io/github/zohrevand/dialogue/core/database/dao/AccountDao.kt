package io.github.zohrevand.dialogue.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.github.zohrevand.dialogue.core.database.model.AccountEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [AccountEntity] access
 */
@Dao
interface AccountDao {
    @Query(
        value = """
        SELECT * FROM accounts
        WHERE id = :accountId
    """
    )
    fun getAccountEntity(accountId: Long): Flow<AccountEntity>

    @Query(value = "SELECT * FROM accounts")
    fun getAccountEntitiesStream(): Flow<List<AccountEntity>>

    /**
     * Inserts [entity] into the db if it doesn't exist, and ignores entity that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAccount(entity: AccountEntity)

    /**
     * Updates [entity] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updateAccount(entity: AccountEntity)

    /**
     * Deletes row in the db matching the specified [id]
     */
    @Query(
        value = """
            DELETE FROM accounts
            WHERE id = :id
        """
    )
    suspend fun deleteAccount(id: Long)

    @Query(value = "DELETE FROM accounts")
    suspend fun deleteAllAccounts()
}