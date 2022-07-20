package io.github.zohrevand.dialogue.core.xmpp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.xmpp.collector.AccountsCollector
import io.github.zohrevand.dialogue.core.xmpp.collector.AccountsCollectorImpl
import io.github.zohrevand.dialogue.core.xmpp.collector.ContactsCollector
import io.github.zohrevand.dialogue.core.xmpp.collector.ContactsCollectorImpl

@Module
@InstallIn(SingletonComponent::class)
interface CollectorModule {

    @Binds
    fun bindsAccountsCollector(
        accountsCollector: AccountsCollectorImpl
    ): AccountsCollector

    @Binds
    fun bindsContactsCollector(
        contactsCollector: ContactsCollectorImpl
    ): ContactsCollector
}
