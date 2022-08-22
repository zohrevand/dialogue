package io.github.zohrevand.dialogue.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.zohrevand.core.model.data.DarkConfig
import io.github.zohrevand.core.model.data.ThemeBranding
import io.github.zohrevand.core.model.data.ThemeConfig
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueDivider
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueLoadingWheel
import io.github.zohrevand.dialogue.core.systemdesign.component.DialogueTopAppBar

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        uiState = uiState,
        onThemeBrandingSelect = viewModel::selectThemeBranding,
        onDarkConfigSelect = viewModel::selectDarkConfig
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onThemeBrandingSelect: (ThemeBranding) -> Unit,
    onDarkConfigSelect: (DarkConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            DialogueTopAppBar(
                titleRes = R.string.settings,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                )
            )
        },
        containerColor = Color.Transparent,
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (uiState) {
                is SettingsUiState.Loading -> {
                    DialogueLoadingWheel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                    )
                }
                is SettingsUiState.Success -> {
                    ThemeBranding(
                        title = "Theme Branding",
                        themeConfig = uiState.themeConfig,
                        themeBrandings = uiState.themeBrandings,
                        onThemeBrandingSelect = onThemeBrandingSelect
                    )

                    DarkConfig(
                        title = "Dark Config",
                        themeConfig = uiState.themeConfig,
                        darkConfigs = uiState.darkConfigs,
                        onDarkConfigSelect = onDarkConfigSelect
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeBranding(
    title: String,
    themeConfig: ThemeConfig,
    themeBrandings: List<ThemeBranding>,
    onThemeBrandingSelect: (ThemeBranding) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        LazyColumn {
            items(themeBrandings, key = { it.name }) { themeBranding ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = themeBranding.name,
                        modifier = Modifier
                    )

                    RadioButton(
                        selected = themeConfig.themeBranding == themeBranding,
                        onClick = { onThemeBrandingSelect(themeBranding) }
                    )
                }

                DialogueDivider()
            }
        }
    }
}

@Composable
private fun DarkConfig(
    title: String,
    themeConfig: ThemeConfig,
    darkConfigs: List<DarkConfig>,
    onDarkConfigSelect: (DarkConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        LazyColumn {
            items(darkConfigs, key = { it.name }) { darkConfig ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = darkConfig.name,
                        modifier = Modifier
                    )

                    RadioButton(
                        selected = themeConfig.darkConfig == darkConfig,
                        onClick = { onDarkConfigSelect(darkConfig) }
                    )
                }

                DialogueDivider()
            }
        }
    }
}
