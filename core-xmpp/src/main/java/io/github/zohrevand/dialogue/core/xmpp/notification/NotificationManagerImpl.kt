package io.github.zohrevand.dialogue.core.xmpp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.media.RingtoneManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.github.zohrevand.dialogue.core.xmpp.R
import javax.inject.Inject
import android.app.NotificationManager as AndroidNotificationManager

const val CHANNEL_ID = "xmpp_service_channel_id"

class NotificationManagerImpl @Inject constructor(
    private val context: Context
) : NotificationManager {

    override fun getNotification(title: String, text: String): Notification {
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            createNotificationChannel()
        }

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_chat)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)
            .build()
    }

    override fun sendNotification(id: Int, notification: Notification) {
        with(NotificationManagerCompat.from(context)) {
            notify(id, notification)
        }
    }

    @RequiresApi(VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = AndroidNotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: AndroidNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
