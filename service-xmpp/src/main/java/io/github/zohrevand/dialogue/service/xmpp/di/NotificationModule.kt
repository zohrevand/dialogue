package io.github.zohrevand.dialogue.core.xmpp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.xmpp.notification.NotificationManager
import io.github.zohrevand.dialogue.core.xmpp.notification.NotificationManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotificationModule {

    @Singleton
    @Provides
    fun providesNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager =
        NotificationManagerImpl(context)
}
