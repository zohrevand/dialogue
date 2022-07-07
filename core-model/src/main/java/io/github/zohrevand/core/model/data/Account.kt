package io.github.zohrevand.core.model.data

data class Account(
    val id: Long? = null,
    val username: String,
    val domain: String,
    val password: String,
    val displayName: String,
    val resource: String,
    val status: AccountStatus
)
