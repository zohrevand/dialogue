package io.github.zohrevand.dialogue.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.zohrevand.dialogue.navigation.DialogueNavHost
import io.github.zohrevand.dialogue.ui.theme.DialogueTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogueApp() {
    DialogueTheme {
        val navController = rememberNavController()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                if (currentDestination?.hierarchy?.any { it.route == "auth_route" } == false) {
                    DialogueBottomBar(
                        onNavigateToTopLevelDestination = {
                            navController.navigate(it) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
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
    onNavigateToTopLevelDestination: (String) -> Unit,
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

        topLevelDestinations.forEach { destination ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == destination } == true
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToTopLevelDestination(destination) },
                label = { Text(destination) },
                alwaysShowLabel = true,
                icon = {
                    val imageVector: ImageVector = if (destination == "conversations_route") {
                        Icons.Filled.Call
                    } else {
                        Icons.Filled.Email
                    }
                    Icon(imageVector = imageVector, contentDescription = null)
                }
            )
        }
    }
}

private val topLevelDestinations = listOf("conversations_route", "contacts_route")