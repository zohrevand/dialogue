package io.github.zohrevand.dialogue.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.data.repository.OfflineFirstPreferencesRepository
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsPreferencesRepository(
        preferencesRepository: OfflineFirstPreferencesRepository
    ): PreferencesRepository
}
