package io.github.zohrevand.dialogue.core.xmpp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.xmpp.MessageManager
import io.github.zohrevand.dialogue.core.xmpp.MessageManagerImpl
import io.github.zohrevand.dialogue.core.xmpp.RosterManager
import io.github.zohrevand.dialogue.core.xmpp.RosterManagerImpl

@Module
@InstallIn(SingletonComponent::class)
interface XmppModule {

    @Binds
    fun bindsRosterManager(
        rosterManager: RosterManagerImpl
    ): RosterManager

    @Binds
    fun bindsMessageManager(
        messageManagerImpl: MessageManagerImpl
    ): MessageManager
}
