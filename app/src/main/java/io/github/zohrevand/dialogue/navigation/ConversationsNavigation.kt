package io.github.zohrevand.dialogue.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import io.github.zohrevand.dialogue.core.navigation.DialogueNavigationDestination
import io.github.zohrevand.dialogue.ui.screens.ConversationsRoute

object ConversationsDestination : DialogueNavigationDestination {
    override val route = "conversations_route"
    override val destination = "conversations_destination"
}

fun NavGraphBuilder.conversationsGraph(
    navigateToChat: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = ConversationsDestination.route,
        startDestination = ConversationsDestination.destination
    ) {
        composable(route = ConversationsDestination.destination) {
            ConversationsRoute(
                navigateToChat = navigateToChat
            )
        }
        nestedGraphs()
    }
}
