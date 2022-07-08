package io.github.zohrevand.dialogue.core.xmpp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.xmpp.collector.AccountsCollector
import io.github.zohrevand.dialogue.core.xmpp.collector.AccountsCollectorImpl

@Module
@InstallIn(SingletonComponent::class)
interface CollectorModule {

    @Binds
    fun bindsAccountsCollector(
        accountsCollector: AccountsCollectorImpl
    ): AccountsCollector
}