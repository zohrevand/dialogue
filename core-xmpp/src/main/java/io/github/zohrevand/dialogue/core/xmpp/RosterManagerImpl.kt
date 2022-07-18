package io.github.zohrevand.dialogue.core.xmpp

import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.roster.PresenceEventListener
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.roster.Roster.SubscriptionMode.accept_all
import org.jivesoftware.smack.roster.RosterListener
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jxmpp.jid.BareJid
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.FullJid
import org.jxmpp.jid.Jid
import org.jxmpp.jid.impl.JidCreate
import org.jxmpp.jid.impl.LocalAndDomainpartJid
import javax.inject.Inject

class RosterManagerImpl @Inject constructor() : RosterManager {

    private lateinit var roster: Roster

    private var rosterListener: RosterListener? = null

    private var presenceEventListener: PresenceEventListener? = null

    init {
        Roster.setDefaultSubscriptionMode(accept_all)
        Roster.setRosterLoadedAtLoginDefault(true)
    }

    override fun initializeRoster(connection: XMPPTCPConnection) {
        roster = Roster.getInstanceFor(connection)

        roster.addRosterListener()

        roster.addPresenceEventListener()
    }

    private fun Roster.addRosterListener() {
        rosterListener = object : RosterListener {
            override fun entriesAdded(addresses: MutableCollection<Jid>?) {
                TODO("Not yet implemented")
            }

            override fun entriesUpdated(addresses: MutableCollection<Jid>?) {
                TODO("Not yet implemented")
            }

            override fun entriesDeleted(addresses: MutableCollection<Jid>?) {
                TODO("Not yet implemented")
            }

            override fun presenceChanged(presence: Presence?) {
                TODO("Not yet implemented")
            }
        }

        addRosterListener(rosterListener)
    }

    private fun Roster.addPresenceEventListener() {
        presenceEventListener = object : PresenceEventListener {
            override fun presenceAvailable(address: FullJid?, availablePresence: Presence?) {
                TODO("Not yet implemented")
            }

            override fun presenceUnavailable(address: FullJid?, presence: Presence?) {
                TODO("Not yet implemented")
            }

            override fun presenceError(address: Jid?, errorPresence: Presence?) {
                TODO("Not yet implemented")
            }

            override fun presenceSubscribed(address: BareJid?, subscribedPresence: Presence?) {
                TODO("Not yet implemented")
            }

            override fun presenceUnsubscribed(address: BareJid?, unsubscribedPresence: Presence?) {
                TODO("Not yet implemented")
            }
        }

        addPresenceEventListener(presenceEventListener)
    }

    private fun createEntry(jid: String) {
        // TODO: check if the nickname (name) is required
        roster.preApproveAndCreateEntry(JidCreate.bareFrom(jid), null, null);
    }

    override fun onCleared() {
        if (this::roster.isInitialized) {
            roster.removeRosterListener(rosterListener)
            roster.removePresenceEventListener(presenceEventListener)
        }
    }
}