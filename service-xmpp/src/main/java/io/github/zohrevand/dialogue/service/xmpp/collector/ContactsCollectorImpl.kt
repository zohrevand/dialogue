package io.github.zohrevand.dialogue.core.xmpp.collector

import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.core.data.repository.ContactsRepository
import javax.inject.Inject

class ContactsCollectorImpl @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ContactsCollector {

    override suspend fun collectShouldAddToRosterContacts(
        addToRoster: suspend (List<Contact>) -> Unit
    ) {
        contactsRepository.getShouldAddToRosterStream().collect { contacts ->
            val updatedContacts = contacts.map { it.copy(shouldAddToRoster = false) }
            contactsRepository.updateContacts(updatedContacts)
            addToRoster(updatedContacts)
        }
    }
}
