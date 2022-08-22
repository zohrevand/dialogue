package io.github.zohrevand.dialogue.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsRoute() {
    SettingsScreen()
}

@Composable
fun SettingsScreen() {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Magenta))
}
