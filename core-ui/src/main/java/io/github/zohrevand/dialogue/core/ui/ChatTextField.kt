package io.github.zohrevand.dialogue.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes placeholderTextId: Int
) {
    DialogueOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = stringResource(placeholderTextId)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
        maxLines = 4,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Gray,
            focusedBorderColor = Color.DarkGray,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.DarkGray
        ),
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        modifier = modifier
    )
}
