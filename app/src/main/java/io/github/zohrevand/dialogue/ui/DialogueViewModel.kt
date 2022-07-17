package io.github.zohrevand.dialogue.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import javax.inject.Inject

@HiltViewModel
class DialogueViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
}

sealed interface ConnectionUiState {
    object Idle : ConnectionUiState

    object Connected : ConnectionUiState

    object Connecting : ConnectionUiState
}