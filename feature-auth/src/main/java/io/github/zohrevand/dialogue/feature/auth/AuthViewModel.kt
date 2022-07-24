package io.github.zohrevand.dialogue.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.core.model.data.AccountStatus.Online
import io.github.zohrevand.core.model.data.AccountStatus.ServerNotFound
import io.github.zohrevand.core.model.data.AccountStatus.Unauthorized
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(jid: String, password: String) {
        val account = Account.create(jid, password)
        _uiState.update { AuthUiState.Loading }
        viewModelScope.launch {
            preferencesRepository.updateAccount(account)

            checkForAccountStatusChanges()
        }
    }

    private suspend fun checkForAccountStatusChanges() {
        preferencesRepository.getAccount().collect { account ->
            when (account.status) {
                Online -> _uiState.update { AuthUiState.Success }
                ServerNotFound -> _uiState.update { AuthUiState.Error("Server not available") }
                Unauthorized -> _uiState.update { AuthUiState.Error("You are not authorized") }
                else -> { /*Not interested*/
                }
            }
        }
    }
}

sealed interface AuthUiState {
    object Idle : AuthUiState

    object Loading : AuthUiState

    object Success : AuthUiState

    data class Error(val message: String) : AuthUiState
}
