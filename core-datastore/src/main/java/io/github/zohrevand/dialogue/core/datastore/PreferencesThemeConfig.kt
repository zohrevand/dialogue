package io.github.zohrevand.dialogue.core.datastore

import io.github.zohrevand.core.model.data.ThemeConfig
import io.github.zohrevand.dialogue.core.datastore.UserPreferences.DarkConfig
import io.github.zohrevand.dialogue.core.datastore.UserPreferences.ThemeBranding

data class PreferencesThemeConfig(
    val themeBranding: ThemeBranding,
    val darkConfig: DarkConfig
)

fun PreferencesThemeConfig.asExternalModel() = ThemeConfig(
    themeBranding = io.github.zohrevand.core.model.data.ThemeBranding.valueOf(themeBranding.name),
    darkConfig = io.github.zohrevand.core.model.data.DarkConfig.valueOf(darkConfig.name)
)
