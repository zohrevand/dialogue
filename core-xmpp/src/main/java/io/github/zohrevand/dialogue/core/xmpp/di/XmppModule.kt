package io.github.zohrevand.dialogue.core.xmpp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.common.coroutines.DialogueDispatchers.IO
import io.github.zohrevand.dialogue.core.common.coroutines.Dispatcher
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import io.github.zohrevand.dialogue.core.xmpp.XmppManager
import io.github.zohrevand.dialogue.core.xmpp.XmppManagerImpl
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class XmppModule {

    @Provides
    @Singleton
    fun providesXmppManager(
        preferencesRepository: PreferencesRepository,
        @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
    ): XmppManager {
        return XmppManagerImpl(preferencesRepository, ioDispatcher)
    }
}