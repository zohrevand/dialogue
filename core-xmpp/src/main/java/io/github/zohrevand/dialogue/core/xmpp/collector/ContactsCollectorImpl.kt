package io.github.zohrevand.dialogue.core.xmpp.collector

import io.github.zohrevand.core.model.data.Contact

class ContactsCollectorImpl : ContactsCollector {

    override suspend fun collectAddToRosterContacts(
        addToRoster: suspend (List<Contact>) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}