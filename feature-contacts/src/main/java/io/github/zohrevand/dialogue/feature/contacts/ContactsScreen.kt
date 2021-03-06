package io.github.zohrevand.dialogue.feature.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.feature.contacts.ContactsUiState.Success

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ContactsRoute(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                Divider(color = Color(0xFFDFDFDF))
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.height(80.dp)
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
