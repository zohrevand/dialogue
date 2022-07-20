package io.github.zohrevand.dialogue.feature.contacts

import androidx.lifecycle.ViewModel
import io.github.zohrevand.dialogue.core.data.repository.ContactsRepository
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

}