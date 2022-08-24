package io.github.zohrevand.dialogue.core.systemdesign.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun DialogueTopAppBar(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    navigationIcon: ImageVector? = null,
    navigationIconContentDescription: String? = null,
    actionIcon: ImageVector? = null,
    actionIconContentDescription: String? = null,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    DialogueTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = navigationIcon,
        navigationIconContentDescription = navigationIconContentDescription,
        actionIcon = actionIcon,
        actionIconContentDescription = actionIconContentDescription,
        colors = colors,
        onNavigationClick = onNavigationClick,
        onActionClick = onActionClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogueTopAppBar(
    modifier: Modifier = Modifier,
    centeredTitle: Boolean = true,
    title: @Composable () -> Unit,
    navigationIcon: ImageVector? = null,
    navigationIconContentDescription: String? = null,
    actionIcon: ImageVector? = null,
    actionIconContentDescription: String? = null,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    val navIcon: @Composable () -> Unit = {
        navigationIcon?.let {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
    val actions: @Composable (RowScope.() -> Unit) = {
        actionIcon?.let {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

    if (centeredTitle) {
        CenterAlignedTopAppBar(
            title = title,
            navigationIcon = navIcon,
            actions = actions,
            colors = colors,
            modifier = modifier
        )
    } else {
        SmallTopAppBar(
            title = title,
            navigationIcon = navIcon,
            actions = actions,
            colors = colors,
            modifier = modifier
        )
    }
}
