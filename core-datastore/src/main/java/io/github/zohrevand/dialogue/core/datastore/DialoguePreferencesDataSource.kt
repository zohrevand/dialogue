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
                authorized = it.connectionAuthorized
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
                        authorized = currentPreferences.connectionAuthorized
                    )
                )

                currentPreferences.copy {
                    connectionAvailability = updatedConnectionStatus.availability
                    connectionAuthorized = updatedConnectionStatus.authorized
                }
            }
        } catch (ioException: IOException) {
            Log.e("DialoguePreferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun getAccount() = userPreferences.data
        .map {
            PreferencesAccount(
                jid = it.accountJid,
                localPart = it.accountLocalPart,
                domainPart = it.accountDomainPart,
                password = it.accountPassword,
                status = it.accountStatus
            )
        }
        .firstOrNull()
}
