package io.github.zohrevand.dialogue.core.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DialoguePreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

    suspend fun getConnectionStatus() = userPreferences.data
        .map {
            ConnectionStatus(
                availability = it.connectionAvailability,
                unauthorized = it.connectionAuthorized,
            )
        }
        .firstOrNull() ?: ConnectionStatus()
}
