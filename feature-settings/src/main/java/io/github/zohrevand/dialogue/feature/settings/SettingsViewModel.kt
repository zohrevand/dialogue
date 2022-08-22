package io.github.zohrevand.dialogue.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.ThemeConfig
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class SettingsViewModel @Inject constructor(
    preferencesRepository: PreferencesRepository
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> =
        preferencesRepository.getThemeConfig()
            .map { themeConfig ->
                SettingsUiState.Success(themeConfig)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState.Loading
            )
}

sealed interface SettingsUiState {
    object Loading : SettingsUiState

    data class Success(val themeConfig: ThemeConfig) : SettingsUiState
}
