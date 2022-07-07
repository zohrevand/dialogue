package io.github.zohrevand.dialogue.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.database.DialogDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDialogDatabase(
        @ApplicationContext context: Context,
    ): DialogDatabase = Room.databaseBuilder(
        context,
        DialogDatabase::class.java,
        "dialogue-database"
    ).build()
}
