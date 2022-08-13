package io.github.zohrevand.dialogue.service.xmpp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.service.xmpp.collector.AccountsCollector
import io.github.zohrevand.dialogue.service.xmpp.collector.AccountsCollectorImpl
import io.github.zohrevand.dialogue.service.xmpp.collector.ChatStateCollector
import io.github.zohrevand.dialogue.service.xmpp.collector.ChatStateCollectorImpl
import io.github.zohrevand.dialogue.service.xmpp.collector.ContactsCollector
import io.github.zohrevand.dialogue.service.xmpp.collector.ContactsCollectorImpl
import io.github.zohrevand.dialogue.service.xmpp.collector.MessagesCollector
import io.github.zohrevand.dialogue.service.xmpp.collector.MessagesCollectorImpl

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
