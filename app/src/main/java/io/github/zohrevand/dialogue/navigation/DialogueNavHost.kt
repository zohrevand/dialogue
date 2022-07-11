package io.github.zohrevand.dialogue.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.github.zohrevand.dialogue.feature.auth.navigation.AuthDestination
import io.github.zohrevand.dialogue.feature.auth.navigation.authGraph

@Composable
fun DialogueNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = AuthDestination.route,
        modifier = modifier,
    ) {
        authGraph(
            navigateToConversations = {
                navController.navigate(ConversationsDestination.route) {
                    popUpTo(AuthDestination.route) {
                        inclusive = true
                    }
                }
            }
        )
        conversationsGraph(
            navigateToChat = { navController.navigate(ChatDestination.route) },
            nestedGraphs = {
                chatGraph(onBackClick = { navController.popBackStack() })
            }
        )
        contactsGraph()
    }
}