package io.github.zohrevand.dialogue.feature.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Success
import io.github.zohrevand.dialogue.feature.chat.navigation.ChatDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val contactId: String = checkNotNull(
        savedStateHandle[ChatDestination.contactJidArg]
    )

    private val _uiState: MutableStateFlow<ChatUiState> = MutableStateFlow(ChatUiState.Loading)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { Success(contactId) }
    }
}

sealed interface ChatUiState {
    data class Success(val contactId: String) : ChatUiState
    object Loading : ChatUiState
}
