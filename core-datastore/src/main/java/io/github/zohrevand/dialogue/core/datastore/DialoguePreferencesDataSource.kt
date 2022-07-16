package io.github.zohrevand.dialogue.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DialoguePreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

    fun getConnectionStatus(): Flow<ConnectionStatus> = userPreferences.data
        .map {
            ConnectionStatus(
                availability = it.connectionAvailability,
                authorized = it.connectionAuthorized
            )
        }

    /**
     * Update the [ConnectionStatus].
     */
    suspend fun updateConnectionStatus(connectionStatus: ConnectionStatus) {
        try {
            userPreferences.updateData { currentPreferences ->
                currentPreferences.copy {
                    connectionAvailability = connectionStatus.availability
                    connectionAuthorized = connectionStatus.authorized
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

    /**
     * Update the [PreferencesAccount] using [update].
     */
    suspend fun updateAccount(update: PreferencesAccount.() -> PreferencesAccount) {
        try {
            userPreferences.updateData { currentPreferences ->
                val updatedAccount = update(
                    PreferencesAccount(
                        jid = currentPreferences.accountJid,
                        localPart = currentPreferences.accountLocalPart,
                        domainPart = currentPreferences.accountDomainPart,
                        password = currentPreferences.accountPassword,
                        status = currentPreferences.accountStatus
                    )
                )

                currentPreferences.copy {
                    accountJid = updatedAccount.jid
                    accountLocalPart = updatedAccount.localPart
                    accountDomainPart = updatedAccount.domainPart
                    accountPassword = updatedAccount.password
                    accountStatus = updatedAccount.status
                }
            }
        } catch (ioException: IOException) {
            Log.e("DialoguePreferences", "Failed to update user preferences", ioException)
        }
    }
}
