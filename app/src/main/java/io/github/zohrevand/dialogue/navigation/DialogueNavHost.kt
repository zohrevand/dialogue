package io.github.zohrevand.dialogue.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import io.github.zohrevand.dialogue.core.navigation.DialogueNavigationDestination
import io.github.zohrevand.dialogue.feature.auth.navigation.AuthDestination
import io.github.zohrevand.dialogue.feature.auth.navigation.authGraph
import io.github.zohrevand.dialogue.feature.chat.navigation.ChatDestination
import io.github.zohrevand.dialogue.feature.chat.navigation.chatGraph
import io.github.zohrevand.dialogue.feature.contacts.navigation.contactsGraph
import io.github.zohrevand.dialogue.feature.conversations.navigation.ConversationsDestination
import io.github.zohrevand.dialogue.feature.conversations.navigation.conversationsGraph
import io.github.zohrevand.dialogue.feature.router.navigation.RouterDestination
import io.github.zohrevand.dialogue.feature.router.navigation.routerGraph

@Composable
fun DialogueNavHost(
    navController: NavHostController,
    onNavigateToDestination: (DialogueNavigationDestination, String) -> Unit,
    onExitChat: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = RouterDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        routerGraph(
            navigateToAuth = {
                navController.navigate(AuthDestination.route) {
                    popUpTo(RouterDestination.route) { inclusive = true }
                }
            },
            navigateToConversations = {
                navController.navigate(ConversationsDestination.route) {
                    popUpTo(RouterDestination.route) { inclusive = true }
                }
            }
        )
        authGraph(
            navigateToConversations = {
                navController.navigate(ConversationsDestination.route) {
                    popUpTo(AuthDestination.route) { inclusive = true }
                }
            }
        )
        conversationsGraph(
            navigateToChat = {
                onNavigateToDestination(
                    ChatDestination, ChatDestination.createNavigationRoute(it)
                )
            },
            nestedGraphs = {
                chatGraph(
                    onBackClick = {
                        onExitChat(it)
                        onBackClick()
                    }
                )
            }
        )
        contactsGraph(
            navigateToChat = {
                onNavigateToDestination(
                    ChatDestination, ChatDestination.createNavigationRoute(it)
                )
            },
        )
    }
}
