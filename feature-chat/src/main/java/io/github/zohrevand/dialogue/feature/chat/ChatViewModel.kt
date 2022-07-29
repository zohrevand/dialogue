package io.github.zohrevand.dialogue.feature.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.ConversationStatus.NotStarted
import io.github.zohrevand.core.model.data.ConversationStatus.Started
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Loading
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Success
import io.github.zohrevand.dialogue.feature.chat.navigation.ChatDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    conversationsRepository: ConversationsRepository
) : ViewModel() {

    private val contactId: String = checkNotNull(
        savedStateHandle[ChatDestination.contactJidArg]
    )

    val uiState: StateFlow<ChatUiState> =
        conversationsRepository.getConversation(peerJid = contactId)
            .map { conversation ->
                if (conversation.status == NotStarted) {
                    conversationsRepository.updateConversation(conversation.copy(status = Started))
                    Loading
                } else {
                    Success(conversation.peerJid)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Loading
            )
}

sealed interface ChatUiState {
    data class Success(val contactId: String) : ChatUiState
    object Loading : ChatUiState
}
