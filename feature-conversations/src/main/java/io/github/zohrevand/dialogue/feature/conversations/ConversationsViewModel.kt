package io.github.zohrevand.dialogue.feature.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import io.github.zohrevand.dialogue.core.data.repository.LastMessagesRepository
import io.github.zohrevand.dialogue.feature.conversations.ConversationsUiState.Loading
import io.github.zohrevand.dialogue.feature.conversations.ConversationsUiState.Success
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    conversationsRepository: ConversationsRepository,
    lastMessagesRepository: LastMessagesRepository
) : ViewModel() {

    private val conversations =
        conversationsRepository.getConversationsStream(status = ConversationStatus.Started)

    private val lastMessages = lastMessagesRepository.getLastMessagesStream()

    val uiState: StateFlow<ConversationsUiState> =
        combine(
            conversations,
            lastMessages
        ) { conversations, lastMessages ->
            Success(
                conversations.map { conversation ->
                    conversation.copy(
                        lastMessage = lastMessages.firstOrNull {
                            it.peerJid == conversation.peerJid
                        }?.lastMessage
                    )
                }
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Loading
            )
}

sealed interface ConversationsUiState {
    object Loading : ConversationsUiState

    data class Success(val conversations: List<Conversation>) : ConversationsUiState
}
