package io.github.zohrevand.dialogue.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import io.github.zohrevand.dialogue.R
import io.github.zohrevand.dialogue.core.navigation.DialogueNavigationDestination
import io.github.zohrevand.dialogue.feature.contacts.navigation.ContactsDestination
import io.github.zohrevand.dialogue.feature.conversations.navigation.ConversationsDestination

/**
 * Models the navigation top level actions in the app.
 */
class DialogueTopLevelNavigation(private val navController: NavHostController) {

    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
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
    }
}

data class TopLevelDestination(
    override val route: String,
    override val destination: String,
    val icon: ImageVector,
    val iconTextId: Int
): DialogueNavigationDestination

val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = ConversationsDestination.route,
        destination = ConversationsDestination.destination,
        icon = Icons.Filled.Star,
        iconTextId = R.string.conversations
    ),
    TopLevelDestination(
        route = ContactsDestination.route,
        destination = ContactsDestination.destination,
        icon = Icons.Filled.Person,
        iconTextId = R.string.contacts
    )
)
