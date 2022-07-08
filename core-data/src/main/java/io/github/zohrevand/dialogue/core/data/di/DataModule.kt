package io.github.zohrevand.dialogue.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.data.repository.AccountsRepository
import io.github.zohrevand.dialogue.core.data.repository.OfflineFirstAccountsRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsAccountRepository(
        accountsRepository: OfflineFirstAccountsRepository
    ): AccountsRepository
}