package io.github.zohrevand.dialogue.core.data.model

import io.github.zohrevand.core.model.data.ConnectionStatus
import io.github.zohrevand.dialogue.core.datastore.PreferencesConnectionStatus

fun ConnectionStatus.asPreferences() = PreferencesConnectionStatus(
    availability = availability,
    authorized = authorized
)
