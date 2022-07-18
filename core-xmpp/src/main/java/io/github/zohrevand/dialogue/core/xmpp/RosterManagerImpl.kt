package io.github.zohrevand.dialogue.core.xmpp

import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.roster.Roster.SubscriptionMode.accept_all
import javax.inject.Inject

class RosterManagerImpl @Inject constructor() : RosterManager {

    init {
        Roster.setDefaultSubscriptionMode(accept_all)
    }
}