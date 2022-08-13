package io.github.zohrevand.dialogue.service.xmpp.collector

import io.github.zohrevand.core.model.data.Contact

interface ContactsCollector {
    /**
     * Collects the changes to contacts stream which should be added
     * to roster entries, originated from database
     * */
    suspend fun collectShouldAddToRosterContacts(
        addToRoster: suspend (List<Contact>) -> Unit
    )
}
