package io.github.zohrevand.dialogue.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.database.DialogueDatabase
import io.github.zohrevand.dialogue.core.database.dao.ContactDao
import io.github.zohrevand.dialogue.core.database.dao.ConversationDao
import io.github.zohrevand.dialogue.core.database.dao.MessageDao
import io.github.zohrevand.dialogue.core.database.dao.SendingChatStateDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesContactDao(
        database: DialogueDatabase,
    ): ContactDao = database.contactDao()

    @Provides
    fun providesMessageDao(
        database: DialogueDatabase,
    ): MessageDao = database.messageDao()

    @Provides
    fun providesConversationDao(
        database: DialogueDatabase,
    ): ConversationDao = database.conversationDao()

    @Provides
    fun providesSendingChatStateDao(
        database: DialogueDatabase,
    ): SendingChatStateDao = database.sendingChatStateDao()
}
