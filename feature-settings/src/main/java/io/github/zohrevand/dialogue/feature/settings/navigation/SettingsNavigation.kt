package io.github.zohrevand.dialogue.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.zohrevand.dialogue.core.navigation.DialogueNavigationDestination
import io.github.zohrevand.dialogue.feature.settings.SettingsRoute

object SettingsDestination : DialogueNavigationDestination {
    override val route = "settings_route"
    override val destination = "settings_destination"
}

fun NavGraphBuilder.routerSettings() {
    composable(
        route = SettingsDestination.route,
    ) {
        SettingsRoute()
    }
}
