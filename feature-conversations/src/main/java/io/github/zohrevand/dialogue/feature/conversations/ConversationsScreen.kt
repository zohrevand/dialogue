package io.github.zohrevand.dialogue.feature.conversations

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.dialogue.feature.conversations.ConversationsUiState.Success

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

@Composable
fun ConversationsScreen(
    uiState: ConversationsUiState,
    navigateToChat: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState is Success) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = modifier
        ) {
            items(uiState.conversations) { conversation ->
                ConversationItem(conversation = conversation)
                Divider(color = Color(0xFFDFDFDF))
            }
        }
    }
}

@Composable
fun ConversationItem(conversation: Conversation) {

}
