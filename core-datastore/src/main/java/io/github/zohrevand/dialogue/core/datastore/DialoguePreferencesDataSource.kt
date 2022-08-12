package io.github.zohrevand.dialogue.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DialoguePreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

    fun getConnectionStatus(): Flow<PreferencesConnectionStatus> = userPreferences.data
        .map {
            PreferencesConnectionStatus(
                availability = it.connectionAvailability,
                authenticated = it.connectionAuthenticated
            )
        }

    /**
     * Update the [PreferencesConnectionStatus].
     */
    suspend fun updateConnectionStatus(connectionStatus: PreferencesConnectionStatus) {
        try {
            userPreferences.updateData { currentPreferences ->
                currentPreferences.copy {
                    connectionAvailability = connectionStatus.availability
                    connectionAuthenticated = connectionStatus.authenticated
                }
            }
        } catch (ioException: IOException) {
            Log.e("DialoguePreferences", "Failed to update user preferences", ioException)
        }
    }

    fun getAccount(): Flow<PreferencesAccount> = userPreferences.data
        .map {
            PreferencesAccount(
                jid = it.accountJid,
                localPart = it.accountLocalPart,
                domainPart = it.accountDomainPart,
                password = it.accountPassword,
                status = it.accountStatus
            )
        }

    /**
     * Update the [PreferencesAccount].
     */
    suspend fun updateAccount(account: PreferencesAccount) {
        try {
            userPreferences.updateData { currentPreferences ->
                currentPreferences.copy {
                    accountJid = account.jid
                    accountLocalPart = account.localPart
                    accountDomainPart = account.domainPart
                    accountPassword = account.password
                    accountStatus = account.status
                }
            }
        } catch (ioException: IOException) {
            Log.e("DialoguePreferences", "Failed to update user preferences", ioException)
        }
    }
}
