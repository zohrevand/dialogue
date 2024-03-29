package io.github.zohrevand.dialogue.ui

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.zohrevand.dialogue.R.string
import io.github.zohrevand.dialogue.core.navigation.NavigationParameters
import io.github.zohrevand.dialogue.feature.auth.navigation.AuthDestination
import io.github.zohrevand.dialogue.feature.chat.navigation.ChatDestination
import io.github.zohrevand.dialogue.feature.contacts.navigation.ContactsDestination
import io.github.zohrevand.dialogue.feature.conversations.navigation.ConversationsDestination
import io.github.zohrevand.dialogue.feature.router.navigation.RouterDestination
import io.github.zohrevand.dialogue.feature.settings.navigation.SettingsDestination
import io.github.zohrevand.dialogue.navigation.TopLevelDestination

@Composable
fun rememberDialogueAppState(
    navController: NavHostController = rememberNavController()
): DialogueAppState {
    return remember(navController) {
        DialogueAppState(navController)
    }
}

@Stable
class DialogueAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBar: Boolean
        @Composable get() {
            val route = currentDestination?.route
            return route != null &&
                route != RouterDestination.route &&
                route != AuthDestination.route &&
                route != ChatDestination.route
        }

    val shouldShowConnecting: Boolean
        @Composable get() {
            val route = currentDestination?.route
            return route != null &&
                route != RouterDestination.route &&
                route != AuthDestination.route
        }

    /**
     * Top level destinations to be used in the BottomBar
     */
    val topLevelDestinations = listOf(
        TopLevelDestination(
            route = ConversationsDestination.route,
            destination = ConversationsDestination.destination,
            icon = Filled.Chat,
            iconTextId = string.conversations
        ),
        TopLevelDestination(
            route = ContactsDestination.route,
            destination = ContactsDestination.destination,
            icon = Filled.Contacts,
            iconTextId = string.contacts
        ),
        TopLevelDestination(
            route = SettingsDestination.route,
            destination = SettingsDestination.destination,
            icon = Filled.Settings,
            iconTextId = string.settings
        )
    )

    /**
     * UI logic for navigating to a particular destination in the app. The NavigationOptions to
     * navigate with are based on the type of destination, which could be a top level destination or
     * just a regular destination.
     *
     * Top level destinations have only one copy of the destination of the back stack, and save and
     * restore state whenever you navigate to and from it.
     * Regular destinations can have multiple copies in the back stack and state isn't saved nor
     * restored.
     *
     * @param parameters: The [NavigationParameters] the app needs to navigate to.
     */
    fun navigate(parameters: NavigationParameters) {
        if (parameters.destination is TopLevelDestination) {
            navController.navigate(parameters.route ?: parameters.destination.route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(ConversationsDestination.destination) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        } else {
            navController.navigate(parameters.route ?: parameters.destination.route) {
                // Custom navOptions to apply for this navigation
                parameters.popUpToInclusive?.let {
                    popUpTo(it.route) { inclusive = true }
                }
            }
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}
