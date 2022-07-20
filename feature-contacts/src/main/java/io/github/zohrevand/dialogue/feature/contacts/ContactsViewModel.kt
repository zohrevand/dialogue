package io.github.zohrevand.dialogue.feature.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.core.model.data.Presence
import io.github.zohrevand.dialogue.core.data.repository.ContactsRepository
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

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