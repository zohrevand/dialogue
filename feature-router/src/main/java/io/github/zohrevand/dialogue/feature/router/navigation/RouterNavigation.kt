package io.github.zohrevand.dialogue.feature.router.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.zohrevand.dialogue.core.navigation.DialogueNavigationDestination
import io.github.zohrevand.dialogue.feature.router.RouterRoute

object RouterDestination : DialogueNavigationDestination {
    override val route = "router_route"
    override val destination = "router_destination"
}

fun NavGraphBuilder.routerGraph(
    navigateToAuth: () -> Unit,
    navigateToConversations: () -> Unit
) {
    composable(
        route = RouterDestination.route,
    ) {
        RouterRoute(
            navigateToAuth = navigateToAuth,
            navigateToConversations = navigateToConversations
        )
    }
}
