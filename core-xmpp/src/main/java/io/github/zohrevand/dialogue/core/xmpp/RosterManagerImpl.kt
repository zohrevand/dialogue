package io.github.zohrevand.dialogue.core.xmpp

import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.roster.Roster.SubscriptionMode.accept_all
import org.jivesoftware.smack.roster.RosterListener
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jxmpp.jid.Jid
import javax.inject.Inject

class RosterManagerImpl @Inject constructor() : RosterManager {

    private lateinit var roster: Roster

    private var rosterListener: RosterListener? = null

    init {
        Roster.setDefaultSubscriptionMode(accept_all)
        Roster.setRosterLoadedAtLoginDefault(true)
    }

    override fun initializeRoster(connection: XMPPTCPConnection) {
        roster = Roster.getInstanceFor(connection)

        roster.addRosterListener()
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

    override fun onCleared() {
        roster.removeRosterListener(rosterListener)
    }
}