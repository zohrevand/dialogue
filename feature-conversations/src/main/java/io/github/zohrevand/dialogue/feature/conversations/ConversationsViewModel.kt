package io.github.zohrevand.dialogue.feature.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import io.github.zohrevand.dialogue.feature.conversations.ConversationsUiState.Loading
import io.github.zohrevand.dialogue.feature.conversations.ConversationsUiState.Success
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    conversationsRepository: ConversationsRepository
) : ViewModel() {

    val uiState: StateFlow<ConversationsUiState> =
        conversationsRepository.getConversationsStream()
            .map { conversations ->
                Success(conversations)
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
