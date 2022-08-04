package io.github.zohrevand.dialogue.feature.conversations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.dialogue.feature.conversations.ConversationsUiState.Success
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationsScreen(
    uiState: ConversationsUiState,
    navigateToChat: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(Color(0xFFE0F7FA))) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))

        CenterAlignedTopAppBar(
            title = {
                if (uiState is Success) {
                    Text(text = stringResource(conversations_title))
                }
            }
        )

        if (uiState is Success) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.conversations) { conversation ->
                    ConversationItem(
                        conversation = conversation,
                        onConversationClick = navigateToChat
                    )
                    Divider(color = Color(0xFFDFDFDF))
                }
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
            if (conversation.draftMessage != null) {
                Text(text = "Draft: ${conversation.draftMessage}", color = Color.LightGray)
            } else if (conversation.lastMessage != null) {
                Text(text = "Draft: ${conversation.lastMessage?.body}", color = Color.LightGray)
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
