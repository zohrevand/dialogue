package io.github.zohrevand.dialogue.feature.auth

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.dialogue.core.common.utils.isValidJid
import io.github.zohrevand.dialogue.feature.auth.R.string
import io.github.zohrevand.dialogue.feature.auth.R.string.hide_password
import io.github.zohrevand.dialogue.feature.auth.R.string.show_password

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

    var jidHasError by remember { mutableStateOf(false) }
    var passwordHasError by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            navigateToConversations()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            // Workaround for safeContentPadding not affecting horizontal paddings
            // for api lower that 32
            .padding(if (VERSION.SDK_INT < VERSION_CODES.S_V2) 32.dp else 0.dp)
            // For layout to scroll when ime is displayed
            .safeContentPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(text = stringResource(string.login_title))

        if (uiState is AuthUiState.Error) {
            GeneralError(uiState.message)
        }

        InputField(
            value = jid,
            onValueChange = {
                setJid(it)
                jidHasError = false
            },
            labelRes = string.jabber_id,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            jidHasError = jidHasError,
            errorRes = string.error_jabber_id_not_valid,
            modifier = modifier.fillMaxWidth()
        )

        InputField(
            value = password,
            onValueChange = {
                setPassword(it)
                passwordHasError = false
            },
            labelRes = string.password,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                VisibilityIcon(
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityChange = { passwordVisible = it }
                )
            },
            jidHasError = passwordHasError,
            errorRes = string.error_password_not_valid,
            modifier = modifier.fillMaxWidth()
        )

        LoginButton(
            uiState = uiState,
            onClick = {
                jidHasError = !jid.isValidJid
                passwordHasError = password.isEmpty()
                if (!jidHasError && !passwordHasError) {
                    onLoginClick(jid, password)
                    focusManager.clearFocus()
                }
            },
            enabled = uiState != AuthUiState.Loading,
        )
    }
}

@Composable
private fun GeneralError(errorMessage: String) {
    Text(text = errorMessage, color = Color.Red)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes labelRes: Int,
    jidHasError: Boolean,
    @StringRes errorRes: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = stringResource(labelRes)) },
            singleLine = true,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            isError = jidHasError,
            modifier = modifier.fillMaxWidth()
        )
        if (jidHasError) {
            Text(
                text = stringResource(errorRes),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
private fun VisibilityIcon(
    passwordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit
) {
    val image = if (passwordVisible)
        Filled.Visibility
    else Filled.VisibilityOff

    val descriptionResId = if (passwordVisible) hide_password else show_password

    IconButton(onClick = { onPasswordVisibilityChange(!passwordVisible) }) {
        Icon(
            imageVector = image,
            contentDescription = stringResource(descriptionResId)
        )
    }
}

@Composable
fun LoginButton(
    uiState: AuthUiState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            disabledContainerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContentColor = MaterialTheme.colorScheme.onSecondary
        ),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(string.login).uppercase(),
                modifier = Modifier.align(Alignment.Center)
            )
            if (uiState == AuthUiState.Loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}
