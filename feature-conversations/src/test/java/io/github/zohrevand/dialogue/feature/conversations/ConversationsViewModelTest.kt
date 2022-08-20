package io.github.zohrevand.dialogue.feature.conversations

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus.Started
import io.github.zohrevand.core.model.data.LastMessage
import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.dialogue.core.testing.repository.TestConversationsRepository
import io.github.zohrevand.dialogue.core.testing.repository.TestLastMessagesRepository
import io.github.zohrevand.dialogue.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConversationsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val conversationsRepository = TestConversationsRepository()
    private val lastMessagesRepository = TestLastMessagesRepository()
    private lateinit var viewModel: ConversationsViewModel

    @Before
    fun setup() {
        viewModel = ConversationsViewModel(
            conversationsRepository = conversationsRepository,
            lastMessagesRepository = lastMessagesRepository
        )
    }

    @Test
    fun uiStateConversations_whenInitialized_thenShowLoading() = runTest {
        Assert.assertEquals(ConversationsUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun uiStateConversations_whenSuccess_thenMatchesConversationsFromRepository() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        conversationsRepository.sendConversations(testConversations)
        lastMessagesRepository.updateLastMessage(testLastMessage)

        val item = viewModel.uiState.value
        Assert.assertTrue(item is ConversationsUiState.Success)

        val successConversationsUiState = item as ConversationsUiState.Success
        val fromRepositoryConversations =
            conversationsRepository.getConversationsStream(Started).first()
        Assert.assertEquals(successConversationsUiState.conversations, fromRepositoryConversations)

        collectJob.cancel()
    }
}

private const val CONVERSATIONS_1_PEER_JID = "hasan@dialogue.im"
private const val CONVERSATIONS_2_PEER_JID = "zohrevand@dialogue.im"
private const val MESSAGE_TEXT = "This is the message"

private val testLastMessage = LastMessage(
    peerJid = CONVERSATIONS_1_PEER_JID,
    lastMessage = Message.create(
        text = MESSAGE_TEXT,
        peerJid = CONVERSATIONS_1_PEER_JID
    )
)

private val testConversations = listOf(
    Conversation(
        peerJid = CONVERSATIONS_1_PEER_JID,
        status = Started,
        lastMessage = testLastMessage.lastMessage
    ),
    Conversation(
        peerJid = CONVERSATIONS_2_PEER_JID,
        status = Started
    )
)
