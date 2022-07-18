package io.github.zohrevand.dialogue.core.xmpp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.common.coroutines.DialogueDispatchers.IO
import io.github.zohrevand.dialogue.core.common.coroutines.Dispatcher
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import io.github.zohrevand.dialogue.core.xmpp.RosterManager
import io.github.zohrevand.dialogue.core.xmpp.XmppManager
import io.github.zohrevand.dialogue.core.xmpp.XmppManagerImpl
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class XmppManagerModule {

    @Provides
    @Singleton
    fun providesXmppManager(
        rosterManager: RosterManager,
        preferencesRepository: PreferencesRepository,
        @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
    ): XmppManager {
        return XmppManagerImpl(
            rosterManager = rosterManager,
            preferencesRepository = preferencesRepository,
            ioDispatcher = ioDispatcher
        )
    }
}