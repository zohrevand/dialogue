package io.github.zohrevand.dialogue.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AuthScreen(
    navigateToConversations: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
    ) {
        Text(text = "This is Login Screen")
        Button(onClick = navigateToConversations) {
            Text(text = "Navigate to conversations")
        }
    }
}