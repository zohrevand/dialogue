package io.github.zohrevand.dialogue.ui

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.zohrevand.dialogue.R.string
import io.github.zohrevand.dialogue.feature.auth.navigation.AuthDestination
import io.github.zohrevand.dialogue.feature.chat.navigation.ChatDestination
import io.github.zohrevand.dialogue.feature.contacts.navigation.ContactsDestination
import io.github.zohrevand.dialogue.feature.conversations.navigation.ConversationsDestination
import io.github.zohrevand.dialogue.feature.router.navigation.RouterDestination
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
        @Composable get() = currentDestination?.route != null &&
            currentDestination?.route != RouterDestination.route &&
            currentDestination?.route != AuthDestination.route &&
            currentDestination?.route != ChatDestination.route

    val shouldShowConnecting: Boolean
        @Composable get() = currentDestination?.route != null &&
            currentDestination?.route != RouterDestination.route &&
            currentDestination?.route != AuthDestination.route

    /**
     * Top level destinations to be used in the BottomBar
     */
    val topLevelDestinations = listOf(
        TopLevelDestination(
            route = ConversationsDestination.route,
            icon = Filled.Star,
            iconTextId = string.conversations
        ),
        TopLevelDestination(
            route = ContactsDestination.route,
            icon = Filled.Person,
            iconTextId = string.contacts
        )
    )
}
