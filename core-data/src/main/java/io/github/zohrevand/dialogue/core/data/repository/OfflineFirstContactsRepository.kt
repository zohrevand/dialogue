package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.core.data.model.asEntity
import io.github.zohrevand.dialogue.core.database.dao.ContactDao
import io.github.zohrevand.dialogue.core.database.model.ContactEntity
import io.github.zohrevand.dialogue.core.database.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstContactsRepository @Inject constructor(
    private val contactDao: ContactDao
) : ContactsRepository {

    override fun getContact(jid: String): Flow<Contact> =
        contactDao.getContactEntity(jid).map { it.asExternalModel() }

    override fun getContactsStream(): Flow<List<Contact>> =
        contactDao.getContactEntitiesStream()
            .map { it.map(ContactEntity::asExternalModel) }

    override fun getContactsStream(jids: Set<String>) =
        contactDao.getContactEntitiesStream(jids)
            .map { it.map(ContactEntity::asExternalModel) }

    override suspend fun updateContacts(contacts: List<Contact>) =
        contactDao.upsert(contacts.map { it.asEntity() })

    override suspend fun deleteContacts(jids: List<String>) =
        contactDao.deleteContacts(jids)
}