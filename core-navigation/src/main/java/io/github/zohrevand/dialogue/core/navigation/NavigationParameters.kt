package io.github.zohrevand.dialogue.core.navigation

data class NavigationParameters(
    val destination: DialogueNavigationDestination,
    val route: String? = null,
    val popUpToInclusive: DialogueNavigationDestination? = null
)
