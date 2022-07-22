package io.github.zohrevand.dialogue.feature.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.feature.contacts.ContactsUiState.Success

@Composable
fun ContactsRoute(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ContactsScreen(
        uiState = uiState,
        addContact = viewModel::addContact,
        modifier = modifier
    )
}

@Composable
fun ContactsScreen(
    uiState: ContactsUiState,
    addContact: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState is Success) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = modifier
        ) {
            items(uiState.contacts) {
                ContactItem(it)
            }
        }
    }

    /*Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        Text(text = "This is Contacts Screen")
        Button(onClick = addContact) {
            Text(text = "Add Contact")
        }
    }*/
}

@Composable
fun ContactItem(contact: Contact) {
    Text(text = contact.jid)
}
