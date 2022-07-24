package io.github.zohrevand.dialogue.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.dialogue.feature.auth.AuthUiState.Error
import io.github.zohrevand.dialogue.feature.auth.AuthUiState.Loading
import io.github.zohrevand.dialogue.feature.auth.R.string
import io.github.zohrevand.dialogue.feature.auth.utils.isValidJid

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun AuthRoute(
    navigateToConversations: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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

    var jidError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

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
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(text = stringResource(string.login_title))

        if (uiState is Error) {
            Text(text = uiState.message, color = Color.Red)
        }

        Column {
            OutlinedTextField(
                value = jid,
                onValueChange = {
                    setJid(it)
                    jidError = false
                },
                label = { Text(text = stringResource(string.jabber_id)) },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                isError = jidError,
                modifier = modifier.fillMaxWidth()
            )
            if (jidError) {
                Text(
                    text = stringResource(string.error_jabber_id_not_valid),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Column {
            OutlinedTextField(
                value = password,
                onValueChange = {
                    setPassword(it)
                    passwordError = false
                },
                label = { Text(text = stringResource(string.password)) },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError,
                modifier = modifier.fillMaxWidth()
            )
            if (passwordError) {
                Text(
                    text = stringResource(string.error_password_not_valid),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Button(
            onClick = {
                jidError = !jid.isValidJid
                passwordError = password.isEmpty()
                if (!jidError && !passwordError) {
                    onLoginClick(jid, password)
                    focusManager.clearFocus()
                }
            },
            enabled = uiState != Loading,
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp)
        ) {
            Text(text = stringResource(string.login))
            if (uiState == Loading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}
