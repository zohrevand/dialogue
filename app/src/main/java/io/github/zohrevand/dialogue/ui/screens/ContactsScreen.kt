package io.github.zohrevand.dialogue.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ContactsRoute(
    modifier: Modifier = Modifier
) {
    ContactsScreen(
        modifier = modifier
    )
}

@Composable
fun ContactsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        Text(text = "This is Contacts Screen")
    }
}