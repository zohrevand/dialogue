package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Account
import io.github.zohrevand.core.model.data.ConnectionStatus
import io.github.zohrevand.core.model.data.ThemeConfig
import io.github.zohrevand.dialogue.core.data.model.asPreferences
import io.github.zohrevand.dialogue.core.datastore.DialoguePreferencesDataSource
import io.github.zohrevand.dialogue.core.datastore.PreferencesAccount
import io.github.zohrevand.dialogue.core.datastore.PreferencesThemeConfig
import io.github.zohrevand.dialogue.core.datastore.asExternalModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstPreferencesRepository @Inject constructor(
    private val preferencesDataSource: DialoguePreferencesDataSource,
) : PreferencesRepository {

    override fun getConnectionStatus(): Flow<ConnectionStatus> =
        preferencesDataSource.getConnectionStatus().map { it.asExternalModel() }

    override suspend fun updateConnectionStatus(connectionStatus: ConnectionStatus) =
        preferencesDataSource.updateConnectionStatus(connectionStatus.asPreferences())

    override fun getAccount(): Flow<Account> =
        preferencesDataSource.getAccount().map(PreferencesAccount::asExternalModel)

    override suspend fun updateAccount(account: Account) =
        preferencesDataSource.updateAccount(account.asPreferences())

    override fun getThemeConfig(): Flow<ThemeConfig> =
        preferencesDataSource.getThemeConfig().map(PreferencesThemeConfig::asExternalModel)

    override suspend fun updateThemeConfig(themeConfig: ThemeConfig) =
        preferencesDataSource.updateThemeConfig(themeConfig.asPreferences())
}
