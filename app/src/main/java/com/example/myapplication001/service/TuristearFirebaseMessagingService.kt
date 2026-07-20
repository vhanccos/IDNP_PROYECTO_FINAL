package com.example.myapplication001.service

import android.util.Log
import com.example.myapplication001.util.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class TuristearFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "TuristearFCM"
    }

    // Se llama cuando llega una notificación push desde Firebase
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "Mensaje FCM recibido de: ${remoteMessage.from}")

        // Notificación con título y cuerpo desde la consola de Firebase
        remoteMessage.notification?.let { notification ->
            Log.d(TAG, "Título: ${notification.title}, Cuerpo: ${notification.body}")
            NotificationHelper.showEventNotification(
                context = applicationContext,
                title = notification.title ?: "Turistear",
                body = notification.body ?: "Hay novedades en Arequipa"
            )
        }

        // Data payload: cuando el mensaje trae datos personalizados
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Data payload: ${remoteMessage.data}")
            val title = remoteMessage.data["title"] ?: "Nuevo evento"
            val body = remoteMessage.data["body"] ?: "Hay un nuevo evento turístico"
            val type = remoteMessage.data["type"] ?: "general"

            when (type) {
                "evento" -> NotificationHelper.showEventNotification(
                    applicationContext, title, body
                )
                "sync" -> NotificationHelper.showSyncNotification(
                    applicationContext, body
                )
                else -> NotificationHelper.showEventNotification(
                    applicationContext, title, body
                )
            }
        }
    }

    // Se llama cuando Firebase genera o rota el token del dispositivo
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Nuevo token FCM: $token")
        // Aquí enviarías el token a tu backend si tuvieras uno
        // Por ahora lo guardamos en SharedPreferences para mostrarlo en la UI
        val prefs = getSharedPreferences("turistear_prefs", MODE_PRIVATE)
        prefs.edit().putString("fcm_token", token).apply()
    }
}