package io.github.zohrevand.dialogue.feature.conversations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueLoadingWheel
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueTopAppBar
import io.github.zohrevand.dialogue.feature.conversations.R.string.conversations_title

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ConversationsRoute(
    navigateToChat: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConversationsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConversationsScreen(
        uiState = uiState,
        navigateToChat = navigateToChat,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ConversationsScreen(
    uiState: ConversationsUiState,
    navigateToChat: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            DialogueTopAppBar(
                titleRes = conversations_title,
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
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .testTag("conversations")
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            conversations(
                conversationsState = uiState,
                navigateToChat = navigateToChat
            )
        }
    }
}

fun LazyListScope.conversations(
    conversationsState: ConversationsUiState,
    navigateToChat: (String) -> Unit
) {
    when (conversationsState) {
        ConversationsUiState.Loading -> {
            item {
                DialogueLoadingWheel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                )
            }
        }
        is ConversationsUiState.Success -> {
            items(conversationsState.conversations, key = { it.peerJid }) { conversation ->
                ConversationItem(
                    conversation = conversation,
                    onConversationClick = navigateToChat
                )
                Divider(color = Color(0xFFDFDFDF))
            }
        }
    }
}

@Composable
fun ConversationItem(
    conversation: Conversation,
    onConversationClick: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .height(80.dp)
            .clickable { onConversationClick(conversation.peerJid) }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.Magenta)
        ) {
            Text(
                text = conversation.peerJid.take(1).uppercase(),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(text = conversation.peerJid)
            val subtitle: String? = if (conversation.draftMessage != null) {
                "Draft: ${conversation.draftMessage}"
            } else if (conversation.lastMessage != null) {
                conversation.lastMessage?.body
            } else {
                null
            }
            subtitle?.let {
                Text(text = it, color = Color.Gray)
            }
        }

        if (conversation.unreadMessagesCount > 0) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4CAF50))
            ) {
                Text(
                    text = conversation.unreadMessagesCount.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
