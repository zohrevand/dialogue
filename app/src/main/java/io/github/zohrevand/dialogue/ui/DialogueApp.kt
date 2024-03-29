package io.github.zohrevand.dialogue.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import io.github.zohrevand.dialogue.R.string.connecting
import io.github.zohrevand.dialogue.core.navigation.NavigationParameters
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueBackground
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueNavigationBar
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueNavigationBarItem
import io.github.zohrevand.dialogue.navigation.DialogueNavHost
import io.github.zohrevand.dialogue.navigation.TopLevelDestination
import io.github.zohrevand.dialogue.ui.ConnectionStatusUiState.Connecting

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLifecycleComposeApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun DialogueApp(
    appState: DialogueAppState = rememberDialogueAppState(),
    viewModel: DialogueViewModel = hiltViewModel()
) {
    val uiState by viewModel.connectionStatusUiState.collectAsStateWithLifecycle()

    DialogueBackground {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) { padding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal
                        )
                    )
            ) {
                DialogueNavHost(
                    navController = appState.navController,
                    onNavigateToDestination = appState::navigate,
                    onExitChat = viewModel::onExitChat,
                    onBackClick = appState::onBackClick,
                    modifier = Modifier
                        .padding(padding)
                        .consumedWindowInsets(padding)
                )

                // Declare bottom bar here instead of Scaffold bottomBar to avoid
                // ChatInput layout of ChatScreen to jump when navigating back.
                // Also wrap it inside AnimatedVisibility in order to conform to
                // navigation transition
                AnimatedVisibility(
                    visible = appState.shouldShowBottomBar,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    DialogueBottomBar(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigate,
                        currentDestination = appState.currentDestination,
                    )
                }

                if (appState.shouldShowConnecting) {
                    Connecting(uiState is Connecting)
                }
            }
        }
    }
}

@Composable
private fun Connecting(isConnecting: Boolean) {
    AnimatedVisibility(
        visible = isConnecting,
        enter = fadeIn() + slideInVertically(),
        exit = slideOutVertically() + fadeOut(),
    ) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(connecting),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun DialogueBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (NavigationParameters) -> Unit,
    currentDestination: NavDestination?
) {
    // Wrap the navigation bar in a surface so the color behind the system
    // navigation is equal to the container color of the navigation bar.
    Surface(color = MaterialTheme.colorScheme.surface) {
        DialogueNavigationBar(
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            )
        ) {
            destinations.forEach { destination ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == destination.route } == true
                DialogueNavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(NavigationParameters(destination)) },
                    label = { Text(stringResource(destination.iconTextId)) },
                    icon = { Icon(imageVector = destination.icon, contentDescription = null) }
                )
            }
        }
    }
}
