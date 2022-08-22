package io.github.zohrevand.dialogue.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.ChatState.Inactive
import io.github.zohrevand.core.model.data.SendingChatState
import io.github.zohrevand.core.model.data.ThemeConfig
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import io.github.zohrevand.dialogue.core.data.repository.SendingChatStatesRepository
import io.github.zohrevand.dialogue.ui.ConnectionStatusUiState.Connected
import io.github.zohrevand.dialogue.ui.ConnectionStatusUiState.Connecting
import io.github.zohrevand.dialogue.ui.ConnectionStatusUiState.Idle
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

    val connectionStatusUiState: StateFlow<ConnectionStatusUiState> =
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

    val themeUiState: StateFlow<ThemeUiState> =
        preferencesRepository.getThemeConfig()
            .map { themeConfig ->
                ThemeUiState.Success(themeConfig)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ThemeUiState.Loading
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

sealed interface ConnectionStatusUiState {
    object Idle : ConnectionStatusUiState

    object Connected : ConnectionStatusUiState

    object Connecting : ConnectionStatusUiState
}

sealed interface ThemeUiState {
    object Loading : ThemeUiState

    data class Success(val themeConfig: ThemeConfig) : ThemeUiState
}
