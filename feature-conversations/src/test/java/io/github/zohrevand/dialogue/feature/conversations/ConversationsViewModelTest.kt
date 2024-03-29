package io.github.zohrevand.dialogue.feature.conversations

import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.dialogue.core.testing.repository.TestConversationsRepository
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
    private lateinit var viewModel: ConversationsViewModel

    @Before
    fun setup() {
        viewModel = ConversationsViewModel(
            conversationsRepository = conversationsRepository
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

        val item = viewModel.uiState.value
        Assert.assertTrue(item is ConversationsUiState.Success)

        val successConversationsUiState = item as ConversationsUiState.Success
        val fromRepositoryConversations =
            conversationsRepository.getConversationsStream().first()
        Assert.assertEquals(successConversationsUiState.conversations, fromRepositoryConversations)

        collectJob.cancel()
    }
}

private const val CONVERSATIONS_1_PEER_JID = "hasan@dialogue.im"
private const val CONVERSATIONS_2_PEER_JID = "zohrevand@dialogue.im"

private val testConversations = listOf(
    Conversation(peerJid = CONVERSATIONS_1_PEER_JID),
    Conversation(peerJid = CONVERSATIONS_2_PEER_JID)
)
