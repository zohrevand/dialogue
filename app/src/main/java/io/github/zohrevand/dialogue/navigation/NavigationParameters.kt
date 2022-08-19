package io.github.zohrevand.dialogue.navigation

import io.github.zohrevand.dialogue.core.navigation.DialogueNavigationDestination

data class NavigationParameters(
    val destination: DialogueNavigationDestination,
    val route: String? = null,
    val popUpToInclusive: DialogueNavigationDestination? = null
)
