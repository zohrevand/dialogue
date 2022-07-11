package io.github.zohrevand.dialogue.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.dialogue.core.data.repository.AccountsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    fun login(jid: String, password: String) {
        val account = Account.create(jid, password)
        viewModelScope.launch {
            accountsRepository.deleteAllAccounts()
            accountsRepository.addAccount(account)
        }
    }
}