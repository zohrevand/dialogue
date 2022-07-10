package io.github.zohrevand.dialogue.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import io.github.zohrevand.dialogue.navigation.DialogueNavHost
import io.github.zohrevand.dialogue.ui.theme.DialogueTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogueApp() {
    DialogueTheme {
        val navController = rememberNavController()

        Scaffold(
            containerColor = Color.Transparent
        ) { padding ->
            DialogueNavHost(
                navController = navController,
                modifier = Modifier.padding(padding)
            )
        }
    }
}