package io.github.zohrevand.dialogue.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DialoguePreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

    suspend fun getConnectionStatus() = userPreferences.data
        .map {
            ConnectionStatus(
                availability = it.connectionAvailability,
                unauthorized = it.connectionAuthorized
            )
        }
        .firstOrNull() ?: ConnectionStatus()

    /**
     * Update the [ConnectionStatus] using [update].
     */
    suspend fun updateConnectionStatus(update: ConnectionStatus.() -> ConnectionStatus) {
        try {
            userPreferences.updateData { currentPreferences ->
                val updatedConnectionStatus = update(
                    ConnectionStatus(
                        availability = currentPreferences.connectionAvailability,
                        unauthorized = currentPreferences.connectionAuthorized
                    )
                )

                currentPreferences.copy {
                    connectionAvailability = updatedConnectionStatus.availability
                    connectionAuthorized = updatedConnectionStatus.unauthorized
                }
            }
        } catch (ioException: IOException) {
            Log.e("NiaPreferences", "Failed to update user preferences", ioException)
        }
    }
}
