package io.github.zohrevand.dialogue.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthRoute(
    navigateToConversations: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    AuthScreen(
        uiState = uiState,
        onLoginClick = viewModel::login,
        navigateToConversations = navigateToConversations,
        modifier = modifier
    )
}

@Composable
fun AuthScreen(
    uiState: AuthUiState,
    onLoginClick: (String, String) -> Unit,
    navigateToConversations: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (jid, setJid) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            navigateToConversations()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(text = "Enter your login information")

        OutlinedTextField(
            value = jid,
            onValueChange = setJid,
            label = { Text(text = "Jabber ID") },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = setPassword,
            label = { Text(text = "Password") },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = modifier.fillMaxWidth()
        )

        Button(
            onClick = { onLoginClick(jid, password) },
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }
}