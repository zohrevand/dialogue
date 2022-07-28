package io.github.zohrevand.dialogue.feature.chat.navigation

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.zohrevand.dialogue.core.navigation.DialogueNavigationDestination
import io.github.zohrevand.dialogue.feature.chat.ChatRoute

object ChatDestination : DialogueNavigationDestination {
    const val contactJidArg = "contactJid"
    override val route = "chat_route/{$contactJidArg}"
    override val destination = "chat_destination"

    /**
     * Creates destination route for a contactJid that could include special characters
     */
    fun createNavigationRoute(contactJidArg: String): String {
        val encodedJid = Uri.encode(contactJidArg)
        return "topic_route/$encodedJid"
    }
}

fun NavGraphBuilder.chatGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = ChatDestination.route,
        arguments = listOf(
            navArgument(ChatDestination.contactJidArg) { type = NavType.StringType}
        )
    ) {
        ChatRoute(onBackClick = onBackClick)
    }
}
