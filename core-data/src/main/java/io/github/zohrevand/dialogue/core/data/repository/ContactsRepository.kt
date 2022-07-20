package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Contact
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {

    fun getContact(jid: String): Flow<Contact>

    fun getContactsStream(): Flow<List<Contact>>

    fun getContactsStream(jids: Set<String>): Flow<List<Contact>>

    fun getAddToRosterStream(): Flow<List<Contact>>

    suspend fun updateContacts(contacts: List<Contact>)

    suspend fun deleteContacts(jids: List<String>)
}
