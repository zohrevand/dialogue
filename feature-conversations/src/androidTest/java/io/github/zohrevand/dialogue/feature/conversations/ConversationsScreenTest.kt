package io.github.zohrevand.dialogue.feature.conversations

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus.Started
import org.junit.Rule
import org.junit.Test

class ConversationsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun conversations_whenConversationsIsLoading_isNotShow() {
        composeTestRule.setContent {
            ConversationsScreen(
                uiState = ConversationsUiState.Loading,
                navigateToChat = {}
            )
        }

        composeTestRule
            .onNodeWithTag("conversations")
            .assertDoesNotExist()
    }
}

private const val CONVERSATIONS_1_PEER_JID = "hasan@dialogue.im"
private const val CONVERSATIONS_2_PEER_JID = "zohrevand@dialogue.im"

private val testConversations = listOf(
    Conversation(peerJid = CONVERSATIONS_1_PEER_JID, status = Started),
    Conversation(peerJid = CONVERSATIONS_2_PEER_JID, status = Started)
)
