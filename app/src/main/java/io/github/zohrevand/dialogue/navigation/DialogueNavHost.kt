package io.github.zohrevand.dialogue.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.zohrevand.dialogue.ui.screens.AuthScreen
import io.github.zohrevand.dialogue.ui.screens.ContactsScreen

@Composable
fun DialogueNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "auth_route",
        modifier = modifier,
    ) {
        composable(route = "auth_route") {
            AuthScreen(
                navigateToConversations = { navController.navigate("conversations_route") {
                    popUpTo("auth_route") {
                        inclusive = true
                    }
                } }
            )
        }

        conversationsGraph(
            navigateToChat = { navController.navigate("chat_route") },
            nestedGraphs = {
                chatGraph(onBackClick = { navController.popBackStack() })
            }
        )

        composable(route = "contacts_route") {
            ContactsScreen()
        }
    }
}