package io.github.zohrevand.dialogue.navigation

import androidx.navigation.NavOptionsBuilder
import io.github.zohrevand.dialogue.core.navigation.DialogueNavigationDestination

data class NavigationParameters(
    val destination: DialogueNavigationDestination,
    val route: String? = null,
    val navOptions: (NavOptionsBuilder.() -> Unit)? = null
)
