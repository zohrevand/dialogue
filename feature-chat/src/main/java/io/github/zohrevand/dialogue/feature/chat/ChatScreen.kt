package io.github.zohrevand.dialogue.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Success
import io.github.zohrevand.dialogue.feature.chat.R.string.message_label
import io.github.zohrevand.dialogue.feature.chat.R.string.send

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
    val (messageText, setMessageText) = remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (uiState is Success) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(Color.Yellow)
            ) {
                Text(text = "This is Chat Screen for ${uiState.conversation.peerJid}")
                Button(onClick = onBackClick) {
                    Text(text = "Back")
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = setMessageText,
                placeholder = { Text(text = stringResource(message_label)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = 4
            )
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Filled.Send,
                    contentDescription = stringResource(send),
                    tint = Color.Blue
                )
            }
        }
    }
}
