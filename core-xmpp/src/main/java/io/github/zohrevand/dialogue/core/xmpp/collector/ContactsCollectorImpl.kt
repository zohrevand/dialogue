package io.github.zohrevand.dialogue.core.xmpp.collector

import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.core.data.repository.ContactsRepository
import javax.inject.Inject

class ContactsCollectorImpl @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ContactsCollector {

    override suspend fun collectAddToRosterContacts(
        addToRoster: suspend (List<Contact>) -> Unit
    ) {
        contactsRepository.getAddToRosterStream().collect { contacts ->
            val updatedContacts = contacts.map { it.copy(addToRoster = false) }
            contactsRepository.updateContacts(updatedContacts)
            addToRoster(contacts)
        }
    }
}