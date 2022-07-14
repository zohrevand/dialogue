package io.github.zohrevand.dialogue.core.datastore

data class ConnectionStatus(
    val availability: Boolean = false,
    val authorized: Boolean = false
)