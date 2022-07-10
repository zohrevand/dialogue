package io.github.zohrevand.dialogue

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.github.zohrevand.dialogue.core.xmpp.XmppService
import io.github.zohrevand.dialogue.ui.DialogueApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startService(Intent(this, XmppService::class.java))

        setContent { DialogueApp() }
    }
}
