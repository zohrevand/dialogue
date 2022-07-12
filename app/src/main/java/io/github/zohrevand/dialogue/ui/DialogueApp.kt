package io.github.zohrevand.dialogue.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.zohrevand.dialogue.feature.auth.navigation.AuthDestination
import io.github.zohrevand.dialogue.navigation.DialogueNavHost
import io.github.zohrevand.dialogue.navigation.DialogueTopLevelNavigation
import io.github.zohrevand.dialogue.navigation.TOP_LEVEL_DESTINATIONS
import io.github.zohrevand.dialogue.navigation.TopLevelDestination
import io.github.zohrevand.dialogue.ui.theme.DialogueTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogueApp() {
    DialogueTheme {
        val navController = rememberNavController()
        val dialogueTopLevelNavigation = remember(navController) {
            DialogueTopLevelNavigation(navController)
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                if (currentDestination?.route != null &&
                    currentDestination.route != AuthDestination.route
                ) {
                    DialogueBottomBar(
                        onNavigateToTopLevelDestination = dialogueTopLevelNavigation::navigateTo,
                        currentDestination = currentDestination
                    )
                }
            }
        ) { padding ->
            DialogueNavHost(
                navController = navController,
                modifier = Modifier
                    .padding(padding)
                    .statusBarsPadding()
            )
        }
    }
}

@Composable
private fun DialogueBottomBar(
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar(
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.safeDrawing.only(
                WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
            )
        ),
        containerColor = Color.Magenta,
        contentColor = Color.Black,
        tonalElevation = 0.dp,
    ) {

        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToTopLevelDestination(destination) },
                label = { Text(stringResource(destination.iconTextId)) },
                alwaysShowLabel = true,
                icon = { Icon(imageVector = destination.icon, contentDescription = null) }
            )
        }
    }
}
