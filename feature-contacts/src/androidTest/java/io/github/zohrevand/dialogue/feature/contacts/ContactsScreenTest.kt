package io.github.zohrevand.dialogue.feature.contacts

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.feature.contacts.ContactsUiState.Loading
import org.junit.Rule
import org.junit.Test

class ContactsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun contacts_whenContactsIsLoading_isNotShow() {
        composeTestRule.setContent {
            ContactsScreen(
                uiState = Loading,
                addContact = {},
                navigateToChat = {}
            )
        }

        composeTestRule
            .onNodeWithText(testContacts[0].jid)
            .assertDoesNotExist()
    }
}

private const val CONTACT_1_JID = "hasan@dialogue.im"
private const val CONTACT_2_JID = "zohrevand@dialogue.im"

private val testContacts = listOf(
    Contact.create(CONTACT_1_JID),
    Contact.create(CONTACT_2_JID)
)
