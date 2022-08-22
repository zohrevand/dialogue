package io.github.zohrevand.dialogue

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.github.zohrevand.core.model.data.DarkConfig
import io.github.zohrevand.core.model.data.ThemeBranding
import io.github.zohrevand.dialogue.core.systemdesign.theme.DialogueTheme
import io.github.zohrevand.dialogue.service.xmpp.XmppService
import io.github.zohrevand.dialogue.ui.DialogueApp
import io.github.zohrevand.dialogue.ui.DialogueViewModel
import io.github.zohrevand.dialogue.ui.ThemeUiState
import io.github.zohrevand.dialogue.ui.ThemeUiState.Loading
import io.github.zohrevand.dialogue.ui.ThemeUiState.Success
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: DialogueViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var themeUiState: ThemeUiState by mutableStateOf(Loading)

        lifecycleScope.launch {
            repeatOnLifecycle(STARTED) {
                viewModel.themeUiState.collect {
                    themeUiState = it
                }
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (themeUiState) {
                is Loading -> true
                is Success -> false
            }
        }

        startService(Intent(this, XmppService::class.java))

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppContent(uiState = themeUiState, viewModel = viewModel)
        }
    }
}

@Composable
fun AppContent(
    uiState: ThemeUiState,
    viewModel: DialogueViewModel
) {
    when (uiState) {
        is Loading -> {}
        is Success -> {
            val themeBranding = uiState.themeConfig.themeBranding
            val darkConfig = uiState.themeConfig.darkConfig

            val darkTheme =
                if (darkConfig == DarkConfig.System)
                    isSystemInDarkTheme()
                else
                    darkConfig == DarkConfig.Dark

            val androidTheme = themeBranding == ThemeBranding.Android

            DialogueTheme(
                darkTheme = darkTheme,
                dynamicColor = supportsDynamicTheming() && androidTheme,
                androidTheme = !supportsDynamicTheming() && androidTheme
            ) {
                DialogueApp(viewModel = viewModel)
            }
        }
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
private fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
