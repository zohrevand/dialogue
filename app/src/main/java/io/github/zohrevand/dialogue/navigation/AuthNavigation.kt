package io.github.zohrevand.dialogue.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.zohrevand.dialogue.ui.screens.AuthRoute

object AuthDestination : DialogueNavigationDestination {
    override val route = "auth_route"
    override val destination = "auth_destination"
}

fun NavGraphBuilder.authGraph(
    navigateToConversations: () -> Unit
) {
    composable(
        route = AuthDestination.route,
    ) {
        AuthRoute(navigateToConversations = navigateToConversations)
    }
}
