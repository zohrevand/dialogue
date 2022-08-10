package io.github.zohrevand.dialogue.core.testing.repository

import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.core.data.repository.ContactsRepository
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class TestContactsRepository : ContactsRepository {

    /**
     * The backing hot flow for the list of contacts for testing
     */
    private val contactsFlow: MutableSharedFlow<List<Contact>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = DROP_OLDEST)

    override fun getContact(jid: String): Flow<Contact> =
        contactsFlow.map { contacts -> contacts.find { it.jid == jid }!! }

    override fun getContactsStream(): Flow<List<Contact>> = contactsFlow

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

    /**
     * A test-only API to allow controlling the list of contacts from tests.
     */
    fun sendContacts(contacts: List<Contact>) {
        contactsFlow.tryEmit(contacts)
    }
}