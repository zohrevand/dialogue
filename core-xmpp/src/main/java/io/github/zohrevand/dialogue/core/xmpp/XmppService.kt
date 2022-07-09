package io.github.zohrevand.dialogue.core.xmpp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class XmppService : Service() {

    @Inject
    lateinit var xmppManager: XmppManager

    override fun onBind(p0: Intent?): IBinder? = null
}