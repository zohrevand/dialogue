package io.github.zohrevand.dialogue.feature.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.core.model.data.Presence
import io.github.zohrevand.dialogue.core.data.repository.ContactsRepository
import io.github.zohrevand.dialogue.feature.contacts.ContactsUiState.Loading
import io.github.zohrevand.dialogue.feature.contacts.ContactsUiState.Success
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    val uiState: StateFlow<ContactsUiState> =
        contactsRepository.getContactsStream()
            .map { contacts ->
                Success(contacts)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Loading
            )

    fun addContact() {
        viewModelScope.launch {
            contactsRepository.updateContacts(listOf(
                Contact(
                    jid = "zohrevand3@conversations.im",
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