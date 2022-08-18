package io.github.zohrevand.dialogue.feature.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import io.github.zohrevand.core.model.data.firstLetter
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueDivider
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueLoadingWheel
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueTopAppBar
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ContactsScreen(
    uiState: ContactsUiState,
    addContact: (String) -> Unit,
    navigateToChat: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isDialogVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DialogueTopAppBar(
                titleRes = contacts_title,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { isDialogVisible = true }) {
                Icon(imageVector = Filled.Add, contentDescription = stringResource(id = add))
            }
        },
        containerColor = Color.Transparent,
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            contacts(
                contactsState = uiState,
                navigateToChat = navigateToChat
            )
        }

        if (isDialogVisible) {
            AddContactDialog(
                addContact = {
                    addContact(it)
                    isDialogVisible = false
                },
                onDismissRequest = { isDialogVisible = false }
            )
        }
    }
}

private fun LazyListScope.contacts(
    contactsState: ContactsUiState,
    navigateToChat: (String) -> Unit,
) {
    when(contactsState) {
        ContactsUiState.Loading -> {
            item {
                DialogueLoadingWheel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                )
            }
        }
        is ContactsUiState.Success -> {
            items(contactsState.contacts, key = { it.jid }) { contact ->
                ContactItem(
                    contact = contact,
                    onContactClick = navigateToChat
                )
                DialogueDivider()
            }
        }
    }
}

@Composable
private fun ContactItem(
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
                text = contact.firstLetter,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Text(text = contact.jid)
    }
}
