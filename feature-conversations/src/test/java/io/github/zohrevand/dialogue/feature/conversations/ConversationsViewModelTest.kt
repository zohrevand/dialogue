package io.github.zohrevand.dialogue.feature.conversations

import io.github.zohrevand.dialogue.core.testing.repository.TestConversationsRepository
import io.github.zohrevand.dialogue.core.testing.util.MainDispatcherRule
import org.junit.Rule

class ConversationsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val conversationsRepository = TestConversationsRepository()
    private lateinit var viewModel: ConversationsViewModel
}