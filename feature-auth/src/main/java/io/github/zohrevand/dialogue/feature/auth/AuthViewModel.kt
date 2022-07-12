package io.github.zohrevand.dialogue.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.dialogue.core.data.repository.AccountsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    private var authInputState: AuthInputState? = null

    fun login(jid: String, password: String) {
        authInputState = AuthInputState(jid)
        val account = Account.create(jid, password)
        viewModelScope.launch {
            accountsRepository.deleteAllAccounts()
            accountsRepository.addAccount(account)
        }
    }
}

data class AuthInputState(
    val jid: String
)
