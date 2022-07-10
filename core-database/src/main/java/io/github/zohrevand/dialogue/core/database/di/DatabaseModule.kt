package io.github.zohrevand.dialogue.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.database.DialogueDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDialogDatabase(
        @ApplicationContext context: Context,
    ): DialogueDatabase = Room.databaseBuilder(
        context,
        DialogueDatabase::class.java,
        "dialogue-database"
    ).build()
}
