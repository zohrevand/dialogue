package io.github.zohrevand.dialogue.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ChatRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ChatScreen(
        onBackClick = onBackClick,
        modifier = modifier
    )
}


@Composable
fun ChatScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Text(text = "This is Chat Screen")
        Button(onClick = onBackClick) {
            Text(text = "Back to conversations")
        }
    }
}