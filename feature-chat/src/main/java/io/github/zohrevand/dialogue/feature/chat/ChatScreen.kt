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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.draw.alpha
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
import io.github.zohrevand.core.model.data.MessageStatus.SentDelivered
import io.github.zohrevand.dialogue.core.common.utils.formatted
import io.github.zohrevand.dialogue.core.common.utils.localTime
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueLoadingWheel
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueTopAppBar
import io.github.zohrevand.dialogue.core.ui.ChatTextField
import io.github.zohrevand.dialogue.core.ui.ContactThumb
import io.github.zohrevand.dialogue.feature.chat.KeyboardState.Opened
import io.github.zohrevand.dialogue.feature.chat.R.string
import io.github.zohrevand.dialogue.feature.chat.R.string.back
import io.github.zohrevand.dialogue.feature.chat.R.string.chat
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

    // Check for new message and scroll down to start of messages
    if (uiState is ChatUiState.Success) {
        LaunchedEffect(uiState.messagesBySendTime.values.flatten().size) {
            scrollState.animateScrollToItem(0)
        }
    }

    LaunchedEffect(keyboardState) {
        if (keyboardState == KeyboardState.Closed) {
            focusManager.clearFocus()
        }
    }

    Scaffold(
        topBar = {
            DialogueTopAppBar(
                title = { TopAppBarTitle(uiState = uiState) },
                centeredTitle = false,
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

    // override back handler to send back contactId
    // in order to reset conversation's isChatOpen property
    BackHandler {
        onBackClick(uiState.contactId)
    }
}

@Composable
private fun TopAppBarTitle(uiState: ChatUiState) {
    when (uiState) {
        is ChatUiState.Loading -> {
            Text(text = stringResource(chat))
        }
        is ChatUiState.Success -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ContactThumb(
                    firstLetter = uiState.conversation.firstLetter,
                    smallShape = true
                )
                Text(
                    text = uiState.conversation.peerLocalPart,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
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
            uiState.messagesBySendTime.forEach { (sendTimeDay, messages) ->
                items(messages, key = { it.id ?: 0 }) { message ->
                    MessageItem(message = message)
                }

                item {
                    MessageDay(sendTimeDay)
                }
            }
        }
    }
}

@Composable
fun MessageDay(
    sendTime: LocalDate,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            text = sendTime.formatted,
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

    Box(
        contentAlignment = style.alignment,
        modifier = modifier.fillMaxWidth()
    ) {
        Surface(
            color = style.containerColor,
            shape = style.shape
        ) {
            Column(modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                MessageBody(message)
                MessageSubtitle(
                    message = message,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun MessageBody(message: Message) {
    Text(text = message.body)
}

@Composable
private fun MessageSubtitle(
    message: Message,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = message.sendTime.localTime,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alpha(0.4f)
        )
        if (message.status == SentDelivered) {
            Icon(
                imageVector = Filled.Check,
                contentDescription = stringResource(string.delivered),
                tint = Color.Blue.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
        }
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
                    tint = MaterialTheme.colorScheme.primary.copy(
                        alpha = if (isSendEnabled) 1f else 0.4f
                    )
                )
            }
        }
    }
}

@Composable
private fun getMessageStyle(isMine: Boolean): MessageStyle {
    return if (isMine) {
        MessageStyle(
            alignment = Alignment.CenterStart,
            containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
            shape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
        )
    } else {
        MessageStyle(
            alignment = Alignment.CenterEnd,
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
            shape = RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
        )
    }
}

private data class MessageStyle(
    val alignment: Alignment,
    val containerColor: Color,
    val shape: Shape
)
