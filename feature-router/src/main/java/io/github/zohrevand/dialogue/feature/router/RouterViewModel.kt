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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.SharingStarted.Companion
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RouterViewModel @Inject constructor(
    preferencesRepository: PreferencesRepository
) : ViewModel() {

    val uiState: StateFlow<RouterUiState> = preferencesRepository.getAccount()
        .map { account ->
            if (account.alreadyLoggedIn) {
                UserAvailable
            } else {
                AuthRequired
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Loading
        )
}

sealed interface RouterUiState {
    object Loading : RouterUiState

    object UserAvailable : RouterUiState

    object AuthRequired : RouterUiState
}
