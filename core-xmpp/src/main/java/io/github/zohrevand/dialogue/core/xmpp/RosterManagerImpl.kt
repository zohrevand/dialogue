package io.github.zohrevand.dialogue.core.xmpp

import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.roster.Roster.SubscriptionMode.accept_all
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import java.lang.IllegalStateException
import javax.inject.Inject

class RosterManagerImpl @Inject constructor() : RosterManager {

    private var roster: Roster? = null

    init {
        Roster.setDefaultSubscriptionMode(accept_all)
        Roster.setRosterLoadedAtLoginDefault(true)
    }

    private fun getRoster(): Roster =
        roster ?: throw IllegalStateException("You should call initializeRoster() first.")

    override fun initializeRoster(connection: XMPPTCPConnection) {
        roster = Roster.getInstanceFor(connection)
    }
}