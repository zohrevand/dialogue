package io.github.zohrevand.dialogue.feature.chat.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.zohrevand.dialogue.core.navigation.DialogueNavigationDestination
import io.github.zohrevand.dialogue.feature.chat.ChatRoute

object ChatDestination : DialogueNavigationDestination {
    override val route = "chat_route"
    override val destination = "chat_destination"
}

fun NavGraphBuilder.chatGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = ChatDestination.route,
    ) {
        ChatRoute(onBackClick = onBackClick)
    }
}
