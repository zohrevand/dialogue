package io.github.zohrevand.dialogue.core.data.model

import io.github.zohrevand.core.model.data.ThemeConfig
import io.github.zohrevand.dialogue.core.datastore.PreferencesThemeConfig
import io.github.zohrevand.dialogue.core.datastore.UserPreferences

fun ThemeConfig.asPreferences() = PreferencesThemeConfig(
    themeBranding = UserPreferences.ThemeBranding.valueOf(themeBranding.name),
    darkConfig = UserPreferences.DarkConfig.valueOf(darkConfig.name)
)