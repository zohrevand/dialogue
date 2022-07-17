package io.github.zohrevand.dialogue.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.zohrevand.dialogue.core.database.model.ContactEntity

@Database(
    entities = [
        ContactEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class DialogueDatabase : RoomDatabase() {

}
