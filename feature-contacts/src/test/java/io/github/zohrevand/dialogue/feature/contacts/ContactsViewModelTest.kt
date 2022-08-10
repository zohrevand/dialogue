package io.github.zohrevand.dialogue.feature.contacts

import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.core.testing.repository.TestContactsRepository
import io.github.zohrevand.dialogue.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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

    @Test
    fun uiStateContacts_whenSuccess_matchesContactsFromRepository() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        contactsRepository.sendContacts(contacts.take(1))

        val item = viewModel.uiState.value
        assertTrue(item is ContactsUiState.Success)

        val successContactsUiState = item as ContactsUiState.Success
        val contact = contactsRepository.getContact(contacts[0].jid).first()
        assertEquals(successContactsUiState.contacts, listOf(contact))

        collectJob.cancel()
    }
}

private val contacts = listOf(
    Contact.create("hasan@dialogue.im"),
    Contact.create("zohrevand@dialogue.im")
)