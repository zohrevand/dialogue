package io.github.zohrevand.dialogue.core.datastore

import androidx.datastore.core.DataStore
import javax.inject.Inject

class DialoguePreferencesDataSource constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

}
