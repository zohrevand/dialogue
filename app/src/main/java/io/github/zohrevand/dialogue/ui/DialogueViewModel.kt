package io.github.zohrevand.dialogue.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.ChatState.Inactive
import io.github.zohrevand.core.model.data.SendingChatState
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import io.github.zohrevand.dialogue.core.data.repository.SendingChatStatesRepository
import io.github.zohrevand.dialogue.ui.ConnectionUiState.Connected
import io.github.zohrevand.dialogue.ui.ConnectionUiState.Connecting
import io.github.zohrevand.dialogue.ui.ConnectionUiState.Idle
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class DialogueViewModel @Inject constructor(
    preferencesRepository: PreferencesRepository,
    private val conversationsRepository: ConversationsRepository,
    private val sendingChatStatesRepository: SendingChatStatesRepository
) : ViewModel() {

    val uiState: StateFlow<ConnectionUiState> =
        preferencesRepository.getConnectionStatus()
            .map { connectionStatus ->
                if (connectionStatus.availability && connectionStatus.authenticated) {
                    Connected
                } else {
                    // TODO: navigate to auth screen if connection available but not authenticated
                    Connecting
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Idle
            )

    fun onExitChat(contactId: String) {
        viewModelScope.launch {
            closeConversation(contactId)
            resetChatState(contactId)
        }
    }

    private suspend fun closeConversation(contactId: String) {
        conversationsRepository.updateConversation(
            peerJid = contactId,
            isChatOpen = false
        )
    }

    private suspend fun resetChatState(contactId: String) {
        sendingChatStatesRepository.updateSendingChatState(
            SendingChatState(peerJid = contactId, chatState = Inactive)
        )
    }
}

sealed interface ConnectionUiState {
    object Idle : ConnectionUiState

    object Connected : ConnectionUiState

    object Connecting : ConnectionUiState
}
