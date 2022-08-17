package io.github.zohrevand.dialogue.feature.chat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.core.model.data.ChatState.Composing
import io.github.zohrevand.core.model.data.ChatState.Paused
import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.core.model.data.isMine
import io.github.zohrevand.core.model.data.peerLocalPart
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueTopAppBar
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Success
import io.github.zohrevand.dialogue.feature.chat.R.string.back
import io.github.zohrevand.dialogue.feature.chat.R.string.message_label
import io.github.zohrevand.dialogue.feature.chat.R.string.send

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ChatRoute(
    onBackClick: (String) -> Unit,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    uiState: ChatUiState,
    onSendMessage: (String) -> Unit,
    onUserTyping: (String) -> Unit,
    onBackClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val draftMessage = if (uiState is Success) uiState.conversation.draftMessage ?: "" else ""
    val (messageText, setMessageText) = remember(draftMessage) { mutableStateOf(draftMessage) }

    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))

        DialogueTopAppBar(
            title = {
                if (uiState is Success) {
                    Text(text = uiState.conversation.peerJid)
                }
            },
            navigationIcon = Filled.ArrowBack,
            navigationIconContentDescription = stringResource(back),
            onNavigationClick = { onBackClick(uiState.contactId) }
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

        if (uiState is Success &&
            (
                uiState.conversation.chatState == Composing ||
                    uiState.conversation.chatState == Paused
                )
        ) {
            val postfixText = if (uiState.conversation.chatState == Composing) "is typing..."
            else "stopped typing."
            Text(
                text = "${uiState.conversation.peerLocalPart} $postfixText",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = {
                    setMessageText(it)
                    onUserTyping(it)
                },
                placeholder = { Text(text = stringResource(message_label)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = 4
            )
            val isSendEnabled = messageText.isNotBlank()
            IconButton(
                onClick = {
                    onSendMessage(messageText)
                    setMessageText("")
                    focusManager.clearFocus()
                },
                enabled = isSendEnabled
            ) {
                Icon(
                    imageVector = Filled.Send,
                    contentDescription = stringResource(send),
                    tint = if (isSendEnabled) Color.Blue else Color.LightGray
                )
            }
        }
    }

    // override back handler to send back contactId
    BackHandler {
        onBackClick(uiState.contactId)
    }
}

@Composable
fun MessagesList(messages: List<Message>) {
    LazyColumn(
        reverseLayout = true,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
        contentPadding = PaddingValues(all = 16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(messages) { message ->
            MessageItem(message = message)
        }
    }
}

@Composable
fun MessageItem(
    message: Message,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        val alignment = if (message.isMine) Alignment.CenterStart else Alignment.CenterEnd
        val horizontalAlignment = if (message.isMine) Alignment.Start else Alignment.End
        val surfaceColor = if (message.isMine) Color(0xFF3F51B5) else Color(0xFFC5CAE9)
        val textColor = if (message.isMine) Color.White else Color.Black
        val bubbleShape =
            if (message.isMine) RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
            else RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)

        Column(
            modifier = Modifier.align(alignment),
            horizontalAlignment = horizontalAlignment
        ) {
            Surface(
                color = surfaceColor,
                shape = bubbleShape
            ) {
                Text(
                    text = message.body,
                    color = textColor,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Text(
                text = message.status.name,
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray
            )
        }
    }
}
