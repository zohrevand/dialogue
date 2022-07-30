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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.dialogue.feature.contacts.ContactsUiState.Success
import io.github.zohrevand.dialogue.feature.contacts.R.string.add
import io.github.zohrevand.dialogue.feature.contacts.R.string.contacts_title

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
    addContact: () -> Unit,
    navigateToChat: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))

            CenterAlignedTopAppBar(
                title = {
                    if (uiState is Success) {
                        Text(text = stringResource(id = contacts_title))
                    }
                }
            )

            if (uiState is Success) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF1FFE1))
                ) {
                    items(uiState.contacts) { contact ->
                        ContactItem(contact = contact, onContactClick = navigateToChat)
                        Divider(color = Color(0xFFDFDFDF))
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = addContact,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Filled.Add, contentDescription = stringResource(id = add))
        }
    }
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
