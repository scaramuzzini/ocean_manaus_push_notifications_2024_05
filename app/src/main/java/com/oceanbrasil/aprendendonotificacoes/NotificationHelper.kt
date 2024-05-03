package com.oceanbrasil.aprendendonotificacoes

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput

class NotificationHelper(private val context: Context) {

    fun criarCanalDeNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Ocean Notificacoes"
            val descriptionText = "Canal oficial de notificacoes OCEAN"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun criarNotificacao(title: String, contextText: String, notificationId: Int, bigText:String = "") {
        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(contextText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    (context as Activity),
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
            // notificationId is a unique int for each notification that you must define.
            notify(notificationId, builder.build())
        }
    }

    fun sendNotificationWithReply() {
        var replyLabel: String = "OCEAN"
        var remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(replyLabel)
            build()
        }

        var replyPendingIntent =
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(
                    context,
                    1,
                    getMessageReplyIntent(1),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )
            } else {
                PendingIntent.getBroadcast(context,
                    0,
                    getMessageReplyIntent(1),
                    PendingIntent.FLAG_UPDATE_CURRENT)
            }


        var action = NotificationCompat.Action.Builder(R.drawable.ic_launcher_foreground,
                "Resposta", replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build()
        val newMessageNotification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Ocean chat")
            .setContentText("Bom dia, tudo bem?")
            .addAction(action)
            .build()

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(NOTIFICATION_WITH_REPLY_ID, newMessageNotification)
        }
    }

    private fun getMessageReplyIntent(i: Int): Intent {
        return Intent(context, DirectReply::class.java)
    }

    companion object {
        const val CHANNEL_FIREBASE_ID = "ocean_firebase_channel"
        const val CHANNEL_ID = "ocean_notification_channel"
        const val NOTIFICATION_ID = 101
        const val NOTIFICATION_OTHER_ID = 102
        const val NOTIFICATION_WITH_REPLY_ID = 103
        const val KEY_TEXT_REPLY = "key_text_reply"
//        var TEMP = "OK"
    }
}