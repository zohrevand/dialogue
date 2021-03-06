package io.github.zohrevand.dialogue.core.xmpp.collector

import io.github.zohrevand.core.model.data.Contact

interface ContactsCollector {
    /**
     * Collects the changes to contacts stream which should be added
     * to roster entries, originated from database
     * */
    suspend fun collectAddToRosterContacts(
        addToRoster: suspend (List<Contact>) -> Unit
    )
}
