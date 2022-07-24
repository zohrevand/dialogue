package io.github.zohrevand.dialogue.feature.router

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.alreadyLoggedIn
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import io.github.zohrevand.dialogue.feature.router.RouterUiState.AuthRequired
import io.github.zohrevand.dialogue.feature.router.RouterUiState.Loading
import io.github.zohrevand.dialogue.feature.router.RouterUiState.UserAvailable
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RouterViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RouterUiState> = MutableStateFlow(Loading)
    val uiState: StateFlow<RouterUiState> = _uiState.asStateFlow()

    init {
        checkIfAccountAlreadyExist()
    }

    private fun checkIfAccountAlreadyExist() {
        viewModelScope.launch {
            val account = preferencesRepository.getAccount().firstOrNull()
            if (account?.alreadyLoggedIn == true) {
                _uiState.update { UserAvailable }
            } else {
                _uiState.update { AuthRequired }
            }
        }
    }
}

sealed interface RouterUiState {
    object Loading : RouterUiState

    object UserAvailable : RouterUiState

    object AuthRequired : RouterUiState
}
