package io.github.zohrevand.dialogue.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.zohrevand.dialogue.core.systemdesign.util.ColorUtil

@Composable
fun ContactThumb(
    modifier: Modifier = Modifier,
    firstLetter: String,
    smallShape: Boolean = false
) {
    Surface(
        color = ColorUtil.getThumbColor(firstLetter[0]),
        shape = CircleShape,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(if (smallShape) 42.dp else 56.dp)
        ) {
            Text(
                text = firstLetter,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
