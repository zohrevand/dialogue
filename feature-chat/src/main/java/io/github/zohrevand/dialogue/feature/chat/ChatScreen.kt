package io.github.zohrevand.dialogue.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Success
import io.github.zohrevand.dialogue.feature.chat.R.string.back
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
        onSendMessage = viewModel::sendMessage,
        onUserTyping = viewModel::userTyping,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun ChatScreen(
    uiState: ChatUiState,
    onSendMessage: (String) -> Unit,
    onUserTyping: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (messageText, setMessageText) = remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.background(Color(0xFFE0F7FA))) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))

        CenterAlignedTopAppBar(
            title = {
                if (uiState is Success) {
                    Text(text = uiState.conversation.peerJid)
                }
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(back)
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            if (uiState is Success) {
                MessagesList(uiState.messages)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = {
                    setMessageText(it)
                    onUserTyping()
                },
                placeholder = { Text(text = stringResource(message_label)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = 4
            )
            IconButton(
                onClick = { onSendMessage(messageText) },
                enabled = messageText.isNotBlank()
            ) {
                Icon(
                    imageVector = Filled.Send,
                    contentDescription = stringResource(send),
                    tint = Color.Blue
                )
            }
        }
    }
}

@Composable
fun MessagesList(messages: List<Message>) {
    LazyColumn(reverseLayout = true) {
        items(messages) { message ->
            MessageItem(message = message)
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    Column {
        Text(text = message.body)
        Text(
            text = message.status.name,
            style = MaterialTheme.typography.bodySmall,
            color = Color.LightGray
        )
    }
}
