package io.github.zohrevand.dialogue.core.datastore

import io.github.zohrevand.dialogue.core.datastore.UserPreferences.DarkConfig
import io.github.zohrevand.dialogue.core.datastore.UserPreferences.ThemeBranding

data class PreferencesThemeConfig(
    val themeBranding: ThemeBranding,
    val darkConfig: DarkConfig
)
