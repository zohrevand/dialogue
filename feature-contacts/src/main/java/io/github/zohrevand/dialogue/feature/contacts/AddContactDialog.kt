package io.github.zohrevand.dialogue.feature.contacts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import io.github.zohrevand.dialogue.core.common.utils.isValidJid
import io.github.zohrevand.dialogue.feature.contacts.R.string

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddContactDialog(
    modifier: Modifier = Modifier,
    onAddContact: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val (newContact, setNewContact) = rememberSaveable { mutableStateOf("") }
    val (contactHasError, setContactHasError) = rememberSaveable { mutableStateOf(false) }

    AlertDialog(
        title = {
            Text(text = stringResource(string.add_contact_title))
        },
        text = {
            AddContactContent(
                contact = newContact,
                setContact = setNewContact,
                contactHasError = contactHasError,
                setContactHasError = setContactHasError
            )
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            AddContactConfirmButton(
                contact = newContact,
                addContact = onAddContact,
                setContactHasError = setContactHasError
            )
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(string.cancel))
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactContent(
    contact: String,
    setContact: (String) -> Unit,
    contactHasError: Boolean,
    setContactHasError: (Boolean) -> Unit
) {
    Column {
        OutlinedTextField(
            value = contact,
            onValueChange = {
                setContact(it)
                setContactHasError(false)
            },
            label = { Text(text = stringResource(string.new_contact)) },
            isError = contactHasError,
            modifier = Modifier.testTag("newContactTextField")
        )
        AnimatedVisibility(visible = contactHasError) {
            Text(
                text = stringResource(string.error_contact_is_not_valid_jabber_id),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun AddContactConfirmButton(
    contact: String,
    addContact: (String) -> Unit,
    setContactHasError: (Boolean) -> Unit
) {
    TextButton(
        onClick = {
            val contactHasError = !contact.isValidJid
            setContactHasError(contactHasError)
            if (!contactHasError) {
                addContact(contact)
            }
        },
        modifier = Modifier.testTag("addContactButton")
    ) {
        Text(text = stringResource(string.add))
    }
}
