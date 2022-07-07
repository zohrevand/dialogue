package io.github.zohrevand.dialogue.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.database.DialogDatabase
import io.github.zohrevand.dialogue.core.database.dao.AccountDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesAccountDao(
        database: DialogDatabase,
    ): AccountDao = database.accountDao()
}
