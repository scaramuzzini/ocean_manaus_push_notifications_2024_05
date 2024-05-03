package com.oceanbrasil.aprendendonotificacoes

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
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
                                NotificationHelper.NOTIFICATION_ID
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