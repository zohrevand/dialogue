package io.github.zohrevand.dialogue.core.datastore

import io.github.zohrevand.core.model.data.ConnectionStatus

data class PreferencesConnectionStatus(
    val availability: Boolean = false,
    val authorized: Boolean = false
)

fun PreferencesConnectionStatus.asExternalModel() = ConnectionStatus(
    availability = availability,
    authorized = authorized
)