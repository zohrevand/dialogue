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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import io.github.zohrevand.dialogue.core.common.utils.isValidJid
import io.github.zohrevand.dialogue.feature.contacts.R.string

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddContactDialog(
    modifier: Modifier = Modifier,
    onAddContact: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val (newContact, setNewContact) = rememberSaveable { mutableStateOf("") }
    var contactError by rememberSaveable { mutableStateOf(false) }

    AlertDialog(
        title = {
            Text(text = stringResource(string.add_contact_title))
        },
        text = {
            Column {
                OutlinedTextField(
                    value = newContact,
                    onValueChange = {
                        setNewContact(it)
                        contactError = false
                    },
                    label = { Text(text = stringResource(string.new_contact)) },
                    isError = contactError,
                    modifier = Modifier.testTag("newContactTextField")
                )
                AnimatedVisibility(visible = contactError) {
                    Text(
                        text = stringResource(string.error_contact_is_not_valid_jabber_id),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    contactError = !newContact.isValidJid
                    if (!contactError) {
                        onAddContact(newContact)
                    }
                },
                modifier = Modifier.testTag("addContactButton")
            ) {
                Text(text = stringResource(string.add))
            }
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