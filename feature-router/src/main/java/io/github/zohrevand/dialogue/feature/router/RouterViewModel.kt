package io.github.zohrevand.dialogue.feature.router

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import io.github.zohrevand.dialogue.feature.router.RouterUiState.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RouterViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel(){

    private val _uiState: MutableStateFlow<RouterUiState> = MutableStateFlow(Loading)
    val uiState: StateFlow<RouterUiState> = _uiState.asStateFlow()

}

sealed interface RouterUiState {
    object Loading : RouterUiState

    object UserAvailable : RouterUiState

    object AuthRequired : RouterUiState
}
