package io.github.zohrevand.dialogue.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import io.github.zohrevand.dialogue.ui.screens.AuthScreen
import io.github.zohrevand.dialogue.ui.screens.ChatScreen
import io.github.zohrevand.dialogue.ui.screens.ContactsScreen
import io.github.zohrevand.dialogue.ui.screens.ConversationsScreen

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

        navigation(
            route = "conversations_route",
            startDestination = "conversations_destination",
        ) {
            composable(route = "conversations_destination") {
                ConversationsScreen(
                    navigateToChat = { navController.navigate("chat_route") }
                )
            }
            composable(route = "chat_route") {
                ChatScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        composable(route = "contacts_route") {
            ContactsScreen()
        }
    }
}