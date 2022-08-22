package io.github.zohrevand.dialogue

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import io.github.zohrevand.core.model.data.DarkConfig
import io.github.zohrevand.core.model.data.DarkConfig.System
import io.github.zohrevand.core.model.data.ThemeBranding
import io.github.zohrevand.core.model.data.ThemeConfig
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import io.github.zohrevand.dialogue.core.systemdesign.theme.DialogueTheme
import io.github.zohrevand.dialogue.service.xmpp.XmppService
import io.github.zohrevand.dialogue.ui.DialogueApp
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var themeConfig: ThemeConfig? by mutableStateOf(null)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(STARTED) {
                preferencesRepository.getThemeConfig().collect {
                    themeConfig = it
                }
            }
        }

        // Keep the splash screen on-screen until the themeConfig is loaded
        splashScreen.setKeepOnScreenCondition {
            when (themeConfig) {
                null -> true
                else -> false
            }
        }

        startService(Intent(this, XmppService::class.java))

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme = themeConfig.shouldUseDarkTheme

            // Update the dark content of the system bars to match the theme
            DisposableEffect(systemUiController, darkTheme) {
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
                onDispose {}
            }

            DialogueTheme(
                darkTheme = darkTheme,
                androidTheme = themeConfig.shouldUseAndroidTheme
            ) {
                DialogueApp()
            }
        }
    }
}

/**
 * Returns `true` if the Android theme should be used.
 */
private val ThemeConfig?.shouldUseAndroidTheme: Boolean
    get() = when (this) {
        null -> false
        else -> themeBranding == ThemeBranding.Android
    }

/**
 * Returns `true` if dark theme should be used
 */
private val ThemeConfig?.shouldUseDarkTheme: Boolean
    @Composable get() = when (this?.darkConfig) {
        null, System -> isSystemInDarkTheme()
        else -> darkConfig == DarkConfig.Dark
    }
