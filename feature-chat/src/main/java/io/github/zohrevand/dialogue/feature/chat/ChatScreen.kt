package io.github.zohrevand.dialogue.feature.chat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.core.model.data.ChatState.Composing
import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.core.model.data.formatted
import io.github.zohrevand.core.model.data.isMine
import io.github.zohrevand.core.model.data.peerLocalPart
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueGradientBackground
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueLoadingWheel
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueTopAppBar
import io.github.zohrevand.dialogue.core.ui.ChatTextField
import io.github.zohrevand.dialogue.feature.chat.KeyboardState.Opened
import io.github.zohrevand.dialogue.feature.chat.R.string.back
import io.github.zohrevand.dialogue.feature.chat.R.string.send
import kotlinx.datetime.LocalDate

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
        sendMessage = viewModel::sendMessage,
        onUserTyping = viewModel::userTyping,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ChatScreen(
    uiState: ChatUiState,
    sendMessage: (String) -> Unit,
    onUserTyping: (String) -> Unit,
    onBackClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    val keyboardState by keyboardAsState()

    LaunchedEffect(uiState) {
        if (uiState is ChatUiState.Success) {
            // Reset scroll if the last message is mine or if the user
            // did not change the list scroll and first message is visible
            // (the reason for less than or equal to 1 is sometimes the
            // first item is peer user's chat state description)
            // TODO: Fix this considering the result messages is a map of messages by sendTime
            val isLastMessageMine =
                uiState.messagesBySendTime.isNotEmpty() /*&& uiState.messages[0].isMine*/
            if (isLastMessageMine || scrollState.firstVisibleItemIndex <= 1) {
                scrollState.animateScrollToItem(0)
            }
        }
    }

    LaunchedEffect(key1 = keyboardState) {
        if (keyboardState == KeyboardState.Closed) {
            focusManager.clearFocus()
        }
    }

    DialogueGradientBackground {
        Scaffold(
            topBar = {
                DialogueTopAppBar(
                    title = {
                        if (uiState is ChatUiState.Success) {
                            Text(text = uiState.conversation.peerJid)
                        }
                    },
                    navigationIcon = Filled.ArrowBack,
                    navigationIconContentDescription = stringResource(back),
                    onNavigationClick = {
                        if (keyboardState == Opened) {
                            focusManager.clearFocus()
                        } else {
                            onBackClick(uiState.contactId)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                    )
                )
            },
            containerColor = Color.Transparent,
            modifier = modifier
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .consumedWindowInsets(innerPadding)
            ) {
                LazyColumn(
                    reverseLayout = true,
                    state = scrollState,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
                    contentPadding = PaddingValues(all = 16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                if (keyboardState == Opened) {
                                    focusManager.clearFocus()
                                }
                            }
                        }
                ) {
                    messages(uiState = uiState)
                }

                ChatState(uiState = uiState)

                ChatInput(
                    uiState = uiState,
                    onUserTyping = onUserTyping,
                    sendMessage = sendMessage
                )
            }
        }
    }

    // override back handler to send back contactId
    // in order to reset conversation's isChatOpen property
    BackHandler {
        onBackClick(uiState.contactId)
    }
}

private fun LazyListScope.messages(uiState: ChatUiState) {
    when (uiState) {
        is ChatUiState.Loading -> {
            item {
                DialogueLoadingWheel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                )
            }
        }
        is ChatUiState.Success -> {
            uiState.messagesBySendTime.forEach { (sendTime, messages) ->
                items(messages, key = { it.id ?: 0 }) { message ->
                    MessageItem(message = message)
                }

                item {
                    MessageTime(sendTime)
                }
            }
        }
    }
}

@Composable
fun MessageTime(
    sendTime: LocalDate,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            text = sendTime.formatted,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun MessageItem(
    message: Message,
    modifier: Modifier = Modifier
) {
    val style = getMessageStyle(message.isMine)

    Column(
        horizontalAlignment = style.horizontalAlignment,
        modifier = modifier.fillMaxWidth()
    ) {
        Surface(
            color = style.containerColor,
            shape = style.shape
        ) {
            Text(
                text = message.body,
                color = style.contentColor,
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

@Composable
private fun ChatState(
    uiState: ChatUiState
) {
    if (uiState is ChatUiState.Success && uiState.shouldShowChatState) {
        val postfixText =
            if (uiState.conversation.chatState == Composing) "is typing..."
            else "stopped typing."
        Text(
            text = "${uiState.conversation.peerLocalPart} $postfixText",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun ChatInput(
    uiState: ChatUiState,
    onUserTyping: (String) -> Unit,
    sendMessage: (String) -> Unit
) {
    val draftMessage = if (uiState is ChatUiState.Success)
        uiState.conversation.draftMessage ?: ""
    else ""

    val (messageText, setMessageText) = rememberSaveable(draftMessage) {
        mutableStateOf(draftMessage)
    }

    Surface(color = MaterialTheme.colorScheme.surface) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChatTextField(
                value = messageText,
                onValueChange = {
                    setMessageText(it)
                    onUserTyping(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .padding(start = 16.dp)
                    .weight(1f)
            )

            val isSendEnabled = messageText.isNotBlank()

            IconButton(
                onClick = {
                    sendMessage(messageText)
                    setMessageText("")
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
}

private fun getMessageStyle(isMine: Boolean): MessageStyle {
    return if (isMine) {
        MessageStyle(
            alignment = Alignment.CenterStart,
            horizontalAlignment = Alignment.Start,
            containerColor = Color(0xFF3F51B5),
            contentColor = Color.White,
            shape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
        )
    } else {
        MessageStyle(
            alignment = Alignment.CenterEnd,
            horizontalAlignment = Alignment.End,
            containerColor = Color(0xFFC5CAE9),
            contentColor = Color.Black,
            shape = RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
        )
    }
}

private data class MessageStyle(
    val alignment: Alignment,
    val horizontalAlignment: Alignment.Horizontal,
    val containerColor: Color,
    val contentColor: Color,
    val shape: Shape
)
