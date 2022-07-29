package io.github.zohrevand.dialogue.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Success

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ChatRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ChatScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun ChatScreen(
    uiState: ChatUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState is Success) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Yellow)
        ) {
            Text(text = "This is Chat Screen for ${uiState.conversation.peerJid}")
            Button(onClick = onBackClick) {
                Text(text = "Back to conversations")
            }
        }
    }
}
