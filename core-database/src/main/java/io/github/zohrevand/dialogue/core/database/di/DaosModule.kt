package io.github.zohrevand.dialogue.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.database.DialogueDatabase
import io.github.zohrevand.dialogue.core.database.dao.ContactDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesContactDao(
        database: DialogueDatabase,
    ): ContactDao = database.contactDao()
}
