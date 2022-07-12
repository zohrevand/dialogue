package io.github.zohrevand.core.model.data

import io.github.zohrevand.core.model.data.AccountStatus.PreLoggingIn

data class Account(
    val id: Long? = null,
    val username: String,
    val domain: String,
    val password: String,
    val displayName: String,
    val resource: String,
    val status: AccountStatus
) {
    companion object {
        fun create(jid: String, password: String): Account {
            val (username, domain) = jid.usernameDomain
            return Account(
                username = username,
                domain = domain,
                password = password,
                status = PreLoggingIn,
                // TODO(): ignore for now
                displayName = "",
                // TODO(): ignore for now
                resource = "",
            )
        }
    }
}

// TODO(): consider jid comprises only local part and domain part for now
val String.usernameDomain: Pair<String, String>
    get() {
        val (username, domain) = this.split("@")
        return Pair(username, domain)
    }
