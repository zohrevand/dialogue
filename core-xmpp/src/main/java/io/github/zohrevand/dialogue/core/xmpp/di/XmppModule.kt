package io.github.zohrevand.dialogue.core.xmpp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.xmpp.XmppManager
import io.github.zohrevand.dialogue.core.xmpp.XmppManagerImpl

@Module
@InstallIn(SingletonComponent::class)
interface XmppModule {

    @Binds
    fun bindsXmppManager(
        xmppManager: XmppManagerImpl
    ): XmppManager
}