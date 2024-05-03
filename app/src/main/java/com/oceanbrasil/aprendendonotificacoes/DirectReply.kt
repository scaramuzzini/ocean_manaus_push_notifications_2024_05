package com.oceanbrasil.aprendendonotificacoes

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.RemoteInput
import com.oceanbrasil.aprendendonotificacoes.NotificationHelper.Companion.KEY_TEXT_REPLY

class DirectReply : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val results: Bundle? = RemoteInput.getResultsFromIntent(intent)

        if (results != null) {
            val mensagem = results.getCharSequence(KEY_TEXT_REPLY).toString()
            Log.d("OCEAN", mensagem)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(NotificationHelper.NOTIFICATION_WITH_REPLY_ID)

        }

    }
}