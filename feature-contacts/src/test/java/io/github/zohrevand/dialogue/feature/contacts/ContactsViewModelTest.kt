package io.github.zohrevand.dialogue.feature.contacts

import io.github.zohrevand.dialogue.core.testing.repository.TestContactsRepository
import io.github.zohrevand.dialogue.core.testing.util.MainDispatcherRule
import org.junit.Before
import org.junit.Rule

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
}