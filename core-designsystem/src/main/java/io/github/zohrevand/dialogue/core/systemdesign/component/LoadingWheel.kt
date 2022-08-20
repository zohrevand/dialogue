package io.github.zohrevand.dialogue.core.systemdesign.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DialogueLoadingWheel(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(modifier = modifier)
}
