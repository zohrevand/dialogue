package io.github.zohrevand.dialogue.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import io.github.zohrevand.dialogue.ui.ConnectionUiState.Connected
import io.github.zohrevand.dialogue.ui.ConnectionUiState.Connecting
import io.github.zohrevand.dialogue.ui.ConnectionUiState.Idle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialogueViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ConnectionUiState> = MutableStateFlow(Idle)
    val uiState: StateFlow<ConnectionUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeConnectionStatus()
        }
    }

    private suspend fun observeConnectionStatus() {
        preferencesRepository.getConnectionStatus().collect { connectionStatus ->
            if (connectionStatus.availability && connectionStatus.authenticated) {
                _uiState.update { Connected }
            } else {
                // TODO: navigate to auth screen if connection available but not authenticated
                _uiState.update { Connecting }
            }
        }
    }
}

sealed interface ConnectionUiState {
    object Idle : ConnectionUiState

    object Connected : ConnectionUiState

    object Connecting : ConnectionUiState
}
