package io.github.zohrevand.dialogue

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreen.KeepOnScreenCondition
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import io.github.zohrevand.dialogue.core.systemdesign.theme.DialogueTheme
import io.github.zohrevand.dialogue.service.xmpp.XmppService
import io.github.zohrevand.dialogue.ui.DialogueApp
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var keepOnSplash by mutableStateOf(true)

        lifecycleScope.launch {
            repeatOnLifecycle(STARTED) {
                preferencesRepository.getThemeConfig().collect {
                    keepOnSplash = false
                }
            }
        }

        splashScreen.setKeepOnScreenCondition {
            keepOnSplash
        }

        startService(Intent(this, XmppService::class.java))

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DialogueTheme {
                DialogueApp()
            }
        }
    }
}
