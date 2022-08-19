package io.github.zohrevand.dialogue.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberDialogueAppState(
    navController: NavHostController = rememberNavController()
): DialogueAppState {
    return remember(navController) {
        DialogueAppState(navController)
    }
}

@Stable
class DialogueAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
}
