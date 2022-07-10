package io.github.zohrevand.dialogue.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.zohrevand.dialogue.core.database.dao.AccountDao
import io.github.zohrevand.dialogue.core.database.model.AccountEntity

@Database(
    entities = [
        AccountEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class DialogueDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
}