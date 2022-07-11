package io.github.zohrevand.dialogue.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AuthRoute(
    navigateToConversations: () -> Unit,
    modifier: Modifier = Modifier
) {
    AuthScreen(
        navigateToConversations = navigateToConversations,
        modifier = modifier
    )
}

@Composable
fun AuthScreen(
    navigateToConversations: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red),
    ) {
        Text(text = "This is Login Screen")
        Button(onClick = navigateToConversations) {
            Text(text = "Navigate to conversations")
        }
    }
}