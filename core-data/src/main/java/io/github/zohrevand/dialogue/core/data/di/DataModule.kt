package io.github.zohrevand.dialogue.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.data.repository.ContactsRepository
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import io.github.zohrevand.dialogue.core.data.repository.LastMessagesRepository
import io.github.zohrevand.dialogue.core.data.repository.MessagesRepository
import io.github.zohrevand.dialogue.core.data.repository.OfflineFirstContactsRepository
import io.github.zohrevand.dialogue.core.data.repository.OfflineFirstConversationsRepository
import io.github.zohrevand.dialogue.core.data.repository.OfflineFirstLastMessagesRepository
import io.github.zohrevand.dialogue.core.data.repository.OfflineFirstMessagesRepository
import io.github.zohrevand.dialogue.core.data.repository.OfflineFirstPreferencesRepository
import io.github.zohrevand.dialogue.core.data.repository.OfflineFirstSendingChatStatesRepository
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import io.github.zohrevand.dialogue.core.data.repository.SendingChatStatesRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsPreferencesRepository(
        preferencesRepository: OfflineFirstPreferencesRepository
    ): PreferencesRepository

    @Binds
    fun bindsContactsRepository(
        contactsRepository: OfflineFirstContactsRepository
    ): ContactsRepository

    @Binds
    fun bindsMessagesRepository(
        messagesRepository: OfflineFirstMessagesRepository
    ): MessagesRepository

    @Binds
    fun bindsConversationsRepository(
        conversationsRepository: OfflineFirstConversationsRepository
    ): ConversationsRepository

    @Binds
    fun bindsSendingChatStatesRepository(
        sendingChatStatesRepository: OfflineFirstSendingChatStatesRepository
    ): SendingChatStatesRepository

    @Binds
    fun bindsLastMessagesRepository(
        lastMessagesRepository: OfflineFirstLastMessagesRepository
    ): LastMessagesRepository
}
