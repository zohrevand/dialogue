package io.github.zohrevand.dialogue.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.zohrevand.dialogue.core.database.dao.ContactDao
import io.github.zohrevand.dialogue.core.database.dao.ConversationDao
import io.github.zohrevand.dialogue.core.database.dao.LastMessageDao
import io.github.zohrevand.dialogue.core.database.dao.MessageDao
import io.github.zohrevand.dialogue.core.database.dao.SendingChatStateDao
import io.github.zohrevand.dialogue.core.database.model.ContactEntity
import io.github.zohrevand.dialogue.core.database.model.ConversationEntity
import io.github.zohrevand.dialogue.core.database.model.LastMessageEntity
import io.github.zohrevand.dialogue.core.database.model.MessageEntity
import io.github.zohrevand.dialogue.core.database.model.SendingChatStateEntity
import io.github.zohrevand.dialogue.core.database.util.InstantConverter

@Database(
    entities = [
        ContactEntity::class,
        MessageEntity::class,
        ConversationEntity::class,
        SendingChatStateEntity::class,
        LastMessageEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    InstantConverter::class,
)
abstract class DialogueDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    abstract fun messageDao(): MessageDao

    abstract fun conversationDao(): ConversationDao

    abstract fun sendingChatStateDao(): SendingChatStateDao

    abstract fun lastMessageDao(): LastMessageDao
}
