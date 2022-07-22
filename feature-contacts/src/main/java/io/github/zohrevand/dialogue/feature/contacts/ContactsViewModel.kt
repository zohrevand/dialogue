package io.github.zohrevand.dialogue.feature.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.core.model.data.Presence
import io.github.zohrevand.dialogue.core.data.repository.ContactsRepository
import io.github.zohrevand.dialogue.feature.contacts.ContactsUiState.Loading
import io.github.zohrevand.dialogue.feature.contacts.ContactsUiState.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ContactsUiState> = MutableStateFlow(Loading)
    val uiState: StateFlow<ContactsUiState> = _uiState.asStateFlow()

    init {
        getContacts()
    }

    private fun getContacts() {
        viewModelScope.launch {
            contactsRepository.getContactsStream().collect { contacts ->
                _uiState.update { Success(contacts) }
            }
        }
    }

    fun addContact() {
        viewModelScope.launch {
            contactsRepository.updateContacts(listOf(
                Contact(
                    jid = "zohrevand@jix.im",
                    presence = Presence(),
                    lastTime = Clock.System.now(),
                    addToRoster = true
                )
            ))
        }
    }
}

sealed interface ContactsUiState {
    object Loading : ContactsUiState

    data class Success(val contacts: List<Contact>) : ContactsUiState
}