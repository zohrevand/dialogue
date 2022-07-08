package io.github.zohrevand.dialogue.core.xmpp.collector

interface AccountsCollector {
    /**
    * Collects the changes to accounts stream originated from database
    * */
    fun collectAccounts()
}