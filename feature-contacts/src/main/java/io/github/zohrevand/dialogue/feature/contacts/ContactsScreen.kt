package io.github.zohrevand.dialogue.feature.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.core.common.utils.isValidJid
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueTopAppBar
import io.github.zohrevand.dialogue.feature.contacts.ContactsUiState.Success
import io.github.zohrevand.dialogue.feature.contacts.R.string.add
import io.github.zohrevand.dialogue.feature.contacts.R.string.add_contact_title
import io.github.zohrevand.dialogue.feature.contacts.R.string.cancel
import io.github.zohrevand.dialogue.feature.contacts.R.string.contacts_title
import io.github.zohrevand.dialogue.feature.contacts.R.string.new_contact

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ContactsRoute(
    modifier: Modifier = Modifier,
    navigateToChat: (String) -> Unit,
    viewModel: ContactsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ContactsScreen(
        uiState = uiState,
        addContact = viewModel::addContact,
        navigateToChat = navigateToChat,
        modifier = modifier
    )
}

@Composable
fun ContactsScreen(
    uiState: ContactsUiState,
    addContact: (String) -> Unit,
    navigateToChat: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isDialogVisible by remember { mutableStateOf(false) }

    Box {
        Column(modifier = modifier.background(Color(0xFFE0F7FA))) {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))

            DialogueTopAppBar(titleRes = contacts_title)

            if (uiState is Success) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.contacts) { contact ->
                        ContactItem(contact = contact, onContactClick = navigateToChat)
                        Divider(color = Color(0xFFDFDFDF))
                    }
                }
            }
        }

        if (isDialogVisible) {
            AddContactDialog(
                onAddContact = addContact,
                onDismissRequest = { isDialogVisible = false }
            )
        }

        FloatingActionButton(
            onClick = { isDialogVisible = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Filled.Add, contentDescription = stringResource(id = add))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddContactDialog(
    modifier: Modifier = Modifier,
    onAddContact: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val (newContact, setNewContact) = remember { mutableStateOf("") }
    var contactError by remember { mutableStateOf(false) }

    AlertDialog(
        title = {
            Text(text = stringResource(add_contact_title))
        },
        text = {
            Column {
                OutlinedTextField(
                    value = newContact,
                    onValueChange = {
                        setNewContact(it)
                        contactError = false
                    },
                    label = { Text(text = stringResource(new_contact)) },
                    isError = contactError,
                )
                Text(
                    text = stringResource(R.string.error_contact_is_not_valid_jabber_id),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        // Workaround to display error when needed,
                        // if we wrap error text with if statement it's not displayed
                        .alpha(if (contactError) 1f else 0f)
                )
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
                }
            ) {
                Text(text = stringResource(add))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(cancel))
            }
        },
        modifier = modifier
    )
}

@Composable
fun ContactItem(
    contact: Contact,
    onContactClick: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .height(80.dp)
            .clickable { onContactClick(contact.jid) }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.Cyan)
        ) {
            Text(
                text = contact.jid.take(1).uppercase(),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Text(text = contact.jid)
    }
}
