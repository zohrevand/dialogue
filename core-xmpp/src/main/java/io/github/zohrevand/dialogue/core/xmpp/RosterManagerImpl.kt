package io.github.zohrevand.dialogue.core.xmpp

import android.util.Log
import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.core.xmpp.collector.ContactsCollector
import javax.inject.Inject
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.roster.PresenceEventListener
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.roster.Roster.SubscriptionMode.accept_all
import org.jivesoftware.smack.roster.RosterListener
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jxmpp.jid.BareJid
import org.jxmpp.jid.FullJid
import org.jxmpp.jid.Jid
import org.jxmpp.jid.impl.JidCreate

private const val TAG = "RosterManager"

@Suppress("BlockingMethodInNonBlockingContext")
class RosterManagerImpl @Inject constructor(
    private val contactsCollector: ContactsCollector
) : RosterManager {

    private lateinit var roster: Roster

    private var rosterListener: RosterListener? = null

    private var presenceEventListener: PresenceEventListener? = null

    init {
        Roster.setDefaultSubscriptionMode(accept_all)
        Roster.setRosterLoadedAtLoginDefault(true)
    }

    override suspend fun initializeRoster(connection: XMPPTCPConnection) {
        roster = Roster.getInstanceFor(connection)

        roster.addRosterListener()

        roster.addPresenceEventListener()

        contactsCollector.collectAddToRosterContacts(this::addToRoster)
    }

    private fun addToRoster(contacts: List<Contact>) {
        contacts.forEach { createEntry(it.jid) }
    }

    private fun createEntry(jid: String) {
        // TODO: check if the nickname (name) is required
        // TODO: check if preApproveAndCreateEntry is the right way to create entry
        roster.preApproveAndCreateEntry(JidCreate.bareFrom(jid), null, null)
    }

    private fun Roster.addRosterListener() {
        rosterListener = object : RosterListener {
            override fun entriesAdded(addresses: MutableCollection<Jid>?) {
                Log.d(TAG, "EntriesAdded $addresses")
            }

            override fun entriesUpdated(addresses: MutableCollection<Jid>?) {
                Log.d(TAG, "entriesUpdated $addresses")
            }

            override fun entriesDeleted(addresses: MutableCollection<Jid>?) {
                Log.d(TAG, "entriesDeleted $addresses")
            }

            override fun presenceChanged(presence: Presence?) {
                Log.d(TAG, "presenceChanged $presence")
            }
        }

        addRosterListener(rosterListener)
    }

    private fun Roster.addPresenceEventListener() {
        presenceEventListener = object : PresenceEventListener {
            override fun presenceAvailable(address: FullJid?, availablePresence: Presence?) {
                Log.d(TAG, "presenceAvailable $address $availablePresence")
            }

            override fun presenceUnavailable(address: FullJid?, presence: Presence?) {
                Log.d(TAG, "presenceUnavailable $address $presence")
            }

            override fun presenceError(address: Jid?, errorPresence: Presence?) {
                Log.d(TAG, "presenceError $address $errorPresence")
            }

            override fun presenceSubscribed(address: BareJid?, subscribedPresence: Presence?) {
                Log.d(TAG, "presenceSubscribed $address $subscribedPresence")
            }

            override fun presenceUnsubscribed(address: BareJid?, unsubscribedPresence: Presence?) {
                Log.d(TAG, "presenceError $address $unsubscribedPresence")
            }
        }

        addPresenceEventListener(presenceEventListener)
    }

    override fun onCleared() {
        if (this::roster.isInitialized) {
            roster.removeRosterListener(rosterListener)
            roster.removePresenceEventListener(presenceEventListener)
        }
    }
}
