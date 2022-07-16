package io.github.zohrevand.dialogue.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.data.repository.AccountsRepository
import io.github.zohrevand.dialogue.core.data.repository.OfflineFirstAccountsRepository
import io.github.zohrevand.dialogue.core.data.repository.OfflineFirstPreferencesRepository
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsAccountRepository(
        accountsRepository: OfflineFirstAccountsRepository
    ): AccountsRepository

    @Binds
    fun bindsPreferencesRepository(
        preferencesRepository: OfflineFirstPreferencesRepository
    ): PreferencesRepository
}
