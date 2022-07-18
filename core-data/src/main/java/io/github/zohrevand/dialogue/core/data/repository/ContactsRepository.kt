package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Contact
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {

    fun getContactsStream(): Flow<List<Contact>>
}