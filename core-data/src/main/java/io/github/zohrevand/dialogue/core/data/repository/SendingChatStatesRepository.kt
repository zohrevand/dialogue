package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.SendingChatState
import kotlinx.coroutines.flow.Flow

interface SendingChatStatesRepository {

    fun getSendingChatStatesStream(): Flow<List<SendingChatState>>

    suspend fun updateSendingChatState(sendingChatState: SendingChatState)
}
