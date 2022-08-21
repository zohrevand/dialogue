package io.github.zohrevand.dialogue.core.database.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.zohrevand.dialogue.core.database.transaction.MessageTransaction
import io.github.zohrevand.dialogue.core.database.transaction.MessageTransactionImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TransactionsModule {

    @Singleton
    @Binds
    fun bindsMessageTransaction(
        messageTransaction: MessageTransactionImpl
    ): MessageTransaction
}
