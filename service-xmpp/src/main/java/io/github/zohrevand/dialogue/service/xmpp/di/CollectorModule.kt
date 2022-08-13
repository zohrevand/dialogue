package io.github.zohrevand.dialogue.core.xmpp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.xmpp.collector.AccountsCollector
import io.github.zohrevand.dialogue.core.xmpp.collector.AccountsCollectorImpl
import io.github.zohrevand.dialogue.core.xmpp.collector.ChatStateCollector
import io.github.zohrevand.dialogue.core.xmpp.collector.ChatStateCollectorImpl
import io.github.zohrevand.dialogue.core.xmpp.collector.ContactsCollector
import io.github.zohrevand.dialogue.core.xmpp.collector.ContactsCollectorImpl
import io.github.zohrevand.dialogue.core.xmpp.collector.MessagesCollector
import io.github.zohrevand.dialogue.core.xmpp.collector.MessagesCollectorImpl

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

    @Binds
    fun bindsMessagesCollector(
        messagesCollector: MessagesCollectorImpl
    ): MessagesCollector

    @Binds
    fun bindsChatStateCollector(
        chatStateCollector: ChatStateCollectorImpl
    ): ChatStateCollector
}
