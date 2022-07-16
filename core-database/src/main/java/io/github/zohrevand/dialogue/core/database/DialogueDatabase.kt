package io.github.zohrevand.dialogue.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1,
    exportSchema = true
)
abstract class DialogueDatabase : RoomDatabase() {

}
