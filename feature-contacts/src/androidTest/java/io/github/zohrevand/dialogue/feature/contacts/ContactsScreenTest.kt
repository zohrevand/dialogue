package io.github.zohrevand.dialogue.feature.contacts

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import io.github.zohrevand.core.model.data.Contact
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ContactsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var addContactFabTitle: String
    private lateinit var addContactDialogTitle: String
    private lateinit var newContactLabel: String
    private lateinit var contactFieldErrorText: String

    @Before
    fun setup() {
        composeTestRule.activity.apply {
            addContactFabTitle = getString(R.string.add)
            addContactDialogTitle = getString(R.string.add_contact_title)
            newContactLabel = getString(R.string.new_contact)
            contactFieldErrorText = getString(R.string.error_contact_is_not_valid_jabber_id)
        }
    }

    @Test
    fun contacts_whenContactsIsLoading_isNotShow() {
        composeTestRule.setContent {
            ContactsScreen(
                uiState = ContactsUiState.Loading,
                addContact = {},
                navigateToChat = {}
            )
        }

        composeTestRule
            .onNodeWithText(testContacts.first().jid)
            .assertDoesNotExist()
    }

    @Test
    fun contacts_whenSuccess_isShown() {
        composeTestRule.setContent {
            ContactsScreen(
                uiState = ContactsUiState.Success(testContacts),
                addContact = {},
                navigateToChat = {}
            )
        }

        composeTestRule
            .onNodeWithText(testContacts.first().jid)
            .assertExists()
    }

    @Test
    fun addContactDialog_whenScreenLoads_isNotShown() {
        composeTestRule.setContent {
            ContactsScreen(
                uiState = ContactsUiState.Success(testContacts),
                addContact = {},
                navigateToChat = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription(addContactFabTitle)
            .assertExists()
            .assertHasClickAction()

        composeTestRule
            .onNodeWithText(addContactDialogTitle)
            .assertDoesNotExist()
    }

    @Test
    fun addContactDialog_whenFabClicked_isShown() {
        composeTestRule.setContent {
            ContactsScreen(
                uiState = ContactsUiState.Success(testContacts),
                addContact = {},
                navigateToChat = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription(addContactFabTitle)
            .assertExists()
            .assertHasClickAction()
            .performClick()

        composeTestRule
            .onNodeWithText(addContactDialogTitle)
            .assertExists()
    }

    @Test
    fun contactTextFieldError_whenEmptyAndAddClicked_isShown() {
        composeTestRule.setContent {
            ContactsScreen(
                uiState = ContactsUiState.Success(testContacts),
                addContact = {},
                navigateToChat = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription(addContactFabTitle)
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithTag("newContactTextField")
            .assertExists()
            .performTextInput("")

        composeTestRule
            .onNodeWithTag("addContactButton")
            .assertExists()
            .assertHasClickAction()
            .performClick()

        composeTestRule
            .onNodeWithText(contactFieldErrorText)
            .assertExists()
    }

    @Test
    fun contactTextFieldError_whenValidAndAddClicked_isNotShown() {
        composeTestRule.setContent {
            ContactsScreen(
                uiState = ContactsUiState.Success(testContacts),
                addContact = {},
                navigateToChat = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription(addContactFabTitle)
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithTag("newContactTextField")
            .assertExists()
            .performTextInput(CONTACT_1_JID)

        composeTestRule
            .onNodeWithTag("addContactButton")
            .assertExists()
            .assertHasClickAction()
            .performClick()

        composeTestRule
            .onNodeWithText(contactFieldErrorText)
            .assertDoesNotExist()
    }
}

private const val CONTACT_1_JID = "hasan@dialogue.im"
private const val CONTACT_2_JID = "zohrevand@dialogue.im"

private val testContacts = listOf(
    Contact.create(CONTACT_1_JID),
    Contact.create(CONTACT_2_JID)
)
