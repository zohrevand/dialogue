package io.github.zohrevand.dialogue.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import io.github.zohrevand.dialogue.core.navigation.NavigationParameters
import io.github.zohrevand.dialogue.feature.auth.navigation.AuthDestination
import io.github.zohrevand.dialogue.feature.auth.navigation.authGraph
import io.github.zohrevand.dialogue.feature.chat.navigation.ChatDestination.createNavigationParameters
import io.github.zohrevand.dialogue.feature.chat.navigation.chatGraph
import io.github.zohrevand.dialogue.feature.contacts.navigation.contactsGraph
import io.github.zohrevand.dialogue.feature.conversations.navigation.ConversationsDestination
import io.github.zohrevand.dialogue.feature.conversations.navigation.conversationsGraph
import io.github.zohrevand.dialogue.feature.router.navigation.RouterDestination
import io.github.zohrevand.dialogue.feature.router.navigation.routerGraph
import io.github.zohrevand.dialogue.feature.settings.navigation.settingsGraph

@Composable
fun DialogueNavHost(
    navController: NavHostController,
    onNavigateToDestination: (NavigationParameters) -> Unit,
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
                onNavigateToDestination(
                    NavigationParameters(
                        destination = AuthDestination,
                        popUpToInclusive = RouterDestination
                    )
                )
            },
            navigateToConversations = {
                onNavigateToDestination(
                    NavigationParameters(
                        destination = ConversationsDestination,
                        popUpToInclusive = RouterDestination
                    )
                )
            }
        )
        authGraph(
            navigateToConversations = {
                onNavigateToDestination(
                    NavigationParameters(
                        destination = ConversationsDestination,
                        popUpToInclusive = AuthDestination
                    )
                )
            }
        )
        conversationsGraph(
            navigateToChat = {
                onNavigateToDestination(createNavigationParameters(it))
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
                onNavigateToDestination(createNavigationParameters(it))
            }
        )
        settingsGraph()
    }
}
