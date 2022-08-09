package io.github.zohrevand.dialogue.core.testing.repository

import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.core.data.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow

class TestContactsRepository : ContactsRepository {

    override fun getContact(jid: String): Flow<Contact> {
        TODO("Not yet implemented")
    }

    override fun getContactsStream(): Flow<List<Contact>> {
        TODO("Not yet implemented")
    }

    override fun getContactsStream(jids: Set<String>): Flow<List<Contact>> {
        TODO("Not yet implemented")
    }

    override fun getShouldAddToRosterStream(): Flow<List<Contact>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateContacts(contacts: List<Contact>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteContacts(jids: List<String>) {
        TODO("Not yet implemented")
    }
}