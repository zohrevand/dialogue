package io.github.zohrevand.dialogue

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.jivesoftware.smack.android.AndroidSmackInitializer

@HiltAndroidApp
class DialogueApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidSmackInitializer.initialize(this)
    }
}
