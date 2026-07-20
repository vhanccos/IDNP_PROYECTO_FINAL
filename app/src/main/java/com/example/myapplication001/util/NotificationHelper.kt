package com.example.myapplication001.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.myapplication001.MainActivity
import com.example.myapplication001.R

object NotificationHelper {

    const val CHANNEL_EVENTOS_ID = "turistear_channel"
    const val CHANNEL_EVENTOS_NAME = "Eventos Turísticos"
    const val CHANNEL_EVENTOS_DESC = "Notificaciones sobre eventos y actividades en Arequipa"

    const val CHANNEL_SYNC_ID = "turistear_sync"
    const val CHANNEL_SYNC_NAME = "Sincronización"
    const val CHANNEL_SYNC_DESC = "Notificaciones de actualización de datos"

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java)

            val eventosChannel = NotificationChannel(
                CHANNEL_EVENTOS_ID,
                CHANNEL_EVENTOS_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_EVENTOS_DESC
                enableLights(true)
                enableVibration(true)
            }

            val syncChannel = NotificationChannel(
                CHANNEL_SYNC_ID,
                CHANNEL_SYNC_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = CHANNEL_SYNC_DESC
            }

            manager.createNotificationChannel(eventosChannel)
            manager.createNotificationChannel(syncChannel)
        }
    }

    // Verifica si el permiso fue concedido por el usuario
    private fun hasNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            // En Android 12 e inferior no se necesita permiso explícito
            true
        }
    }

    fun showEventNotification(
        context: Context,
        title: String,
        body: String,
        notificationId: Int = System.currentTimeMillis().toInt()
    ) {
        // Salir silenciosamente si el permiso no fue concedido
        if (!hasNotificationPermission(context)) return

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("destination", "events")
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_EVENTOS_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(notificationId, notification)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun showSyncNotification(context: Context, message: String) {
        if (!hasNotificationPermission(context)) return

        val notification = NotificationCompat.Builder(context, CHANNEL_SYNC_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Turistear actualizado")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(2001, notification)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}