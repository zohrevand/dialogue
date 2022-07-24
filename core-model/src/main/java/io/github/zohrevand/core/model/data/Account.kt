package io.github.zohrevand.core.model.data

import io.github.zohrevand.core.model.data.AccountStatus.Disabled
import io.github.zohrevand.core.model.data.AccountStatus.Offline
import io.github.zohrevand.core.model.data.AccountStatus.Online
import io.github.zohrevand.core.model.data.AccountStatus.PreLoggingIn

data class Account(
    val jid: String,
    val localPart: String,
    val domainPart: String,
    val password: String,
    val status: AccountStatus
) {
    companion object {
        fun create(jid: String, password: String): Account {
            val (localPart, domainPart) = jid.localPartDomainPart
            return Account(
                jid = jid,
                localPart = localPart,
                domainPart = domainPart,
                password = password,
                status = PreLoggingIn
            )
        }
    }
}

val Account.alreadyLoggedIn: Boolean
    get() = status == Online ||
        status == Disabled ||
        status == Offline

// TODO: consider jid comprises only local part and domain part for now
private val String.localPartDomainPart: Pair<String, String>
    get() {
        val (localPart, domainPart) = this.split("@")
        return Pair(localPart, domainPart)
    }
