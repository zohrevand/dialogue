package io.github.zohrevand.dialogue.feature.contacts

import io.github.zohrevand.dialogue.core.testing.repository.TestContactsRepository
import io.github.zohrevand.dialogue.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ContactsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val contactsRepository = TestContactsRepository()
    private lateinit var viewModel: ContactsViewModel

    @Before
    fun setup() {
        viewModel = ContactsViewModel(
            contactsRepository = contactsRepository
        )
    }

    @Test
    fun uiStateContacts_whenInitialized_thenShowLoading() = runTest {
        assertEquals(ContactsUiState.Loading, viewModel.uiState.value)
    }
}