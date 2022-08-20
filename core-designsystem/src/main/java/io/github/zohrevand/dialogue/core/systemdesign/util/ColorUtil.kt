package io.github.zohrevand.dialogue.core.systemdesign.util

import androidx.compose.ui.graphics.Color
import io.github.zohrevand.dialogue.core.systemdesign.theme.Amber400
import io.github.zohrevand.dialogue.core.systemdesign.theme.Blue400
import io.github.zohrevand.dialogue.core.systemdesign.theme.DeepOrange400
import io.github.zohrevand.dialogue.core.systemdesign.theme.DeepPurple400
import io.github.zohrevand.dialogue.core.systemdesign.theme.Green400
import io.github.zohrevand.dialogue.core.systemdesign.theme.Indigo400
import io.github.zohrevand.dialogue.core.systemdesign.theme.Lime400
import io.github.zohrevand.dialogue.core.systemdesign.theme.Pink400
import io.github.zohrevand.dialogue.core.systemdesign.theme.Red400
import io.github.zohrevand.dialogue.core.systemdesign.theme.Teal400

object ColorUtil {
    private val thumbColors = listOf(
        Red400,
        Pink400,
        DeepPurple400,
        Indigo400,
        Blue400,
        Teal400,
        Green400,
        Lime400,
        Amber400,
        DeepOrange400
    )

    fun getThumbColor(letter: Char): Color {
        return thumbColors[Character.getNumericValue(letter) % thumbColors.size]
    }
}
