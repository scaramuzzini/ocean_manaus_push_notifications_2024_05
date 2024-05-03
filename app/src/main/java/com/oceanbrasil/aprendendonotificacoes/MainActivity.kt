package com.oceanbrasil.aprendendonotificacoes

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.oceanbrasil.aprendendonotificacoes.ui.theme.AprendendoNotificacoesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
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
        }
        val notificationHelper = NotificationHelper(context)
        notificationHelper.criarCanalDeNotificacao()

        setContent {
            AprendendoNotificacoesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {

                        Button(onClick = {
                            notificationHelper.criarNotificacao(
                                "Ocean Aula Push Notification",
                                "Esta e' uma aula de push notification no Ocean/UEA",
                                NotificationHelper.NOTIFICATION_ID,
                                "Este e' um texto mais longo para a notificacao quando ela for expandida devidamente pelo usuario que deseja ver o conteudo completo"
                            )
                        }) {
                            Text("Lançar notificação")
                        }

                        Button(onClick = {
                            notificationHelper.criarNotificacao(
                                "Outro botao",
                                "Esta e' uma notificacao diferente",
                                NotificationHelper.NOTIFICATION_OTHER_ID
                            )
                        }) {
                            Text("Outra notificação")
                        }


                        Button(onClick = {
                            notificationHelper.sendNotificationWithReply()
                        }) {
                            Text(" notificação chat")
                        }
                        Button(onClick = {
                            Firebase.messaging.token.addOnCompleteListener(
                                OnCompleteListener { task ->
                                    if (!task.isSuccessful) {
                                        Log.w("OCEAN", "Fetching FCM registration token failed", task.exception)
                                        return@OnCompleteListener
                                    }

                                    // Get new FCM registration token
                                    val token = task.result

                                    // Log and toast
                                    val msg = token
                                    Log.d("OCEAN", msg)
                                },
                            )

                        }) {
                            Text("FIREBASE Token")
                        }

                    }
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AprendendoNotificacoesTheme {
        Greeting("Android")
    }
}