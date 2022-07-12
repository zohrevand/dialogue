package io.github.zohrevand.dialogue.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.dialogue.core.data.repository.AccountsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private var authInputState: AuthInputState? = null

    fun login(jid: String, password: String) {
        authInputState = AuthInputState(jid)
        val account = Account.create(jid, password)
        _uiState.update { AuthUiState.Loading }
        viewModelScope.launch {
            accountsRepository.deleteAllAccounts()
            accountsRepository.addAccount(account)
        }
    }
}

data class AuthInputState(
    val jid: String
)

sealed interface AuthUiState {
    object Idle : AuthUiState

    object Loading : AuthUiState

    object Success : AuthUiState

    data class Error(val message: String) : AuthUiState
}
