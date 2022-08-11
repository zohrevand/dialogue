package io.github.zohrevand.dialogue.feature.conversations

import io.github.zohrevand.dialogue.core.testing.repository.TestConversationsRepository
import io.github.zohrevand.dialogue.core.testing.util.MainDispatcherRule
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
}