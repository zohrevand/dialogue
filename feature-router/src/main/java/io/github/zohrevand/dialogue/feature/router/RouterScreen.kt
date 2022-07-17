package io.github.zohrevand.dialogue.feature.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.zohrevand.dialogue.feature.router.RouterUiState.AuthRequired
import io.github.zohrevand.dialogue.feature.router.RouterUiState.UserAvailable

@Composable
fun RouterRoute(
    navigateToAuth: () -> Unit,
    navigateToConversations: () -> Unit,
    viewModel: RouterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    RouterScreen(
        uiState = uiState,
        navigateToAuth = navigateToAuth,
        navigateToConversations = navigateToConversations
    )
}

@Composable
fun RouterScreen(
    uiState: RouterUiState,
    navigateToAuth: () -> Unit,
    navigateToConversations: () -> Unit,
) {

    LaunchedEffect(uiState) {
        when (uiState) {
            is UserAvailable -> navigateToConversations()
            is AuthRequired -> navigateToAuth()
            else -> {}
        }
    }
}
