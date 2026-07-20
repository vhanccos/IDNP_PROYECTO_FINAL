package com.example.myapplication001.model

data class TuristearNotification(
    val id: Int = System.currentTimeMillis().toInt(),
    val title: String,
    val body: String,
    val type: NotificationType = NotificationType.EVENTO,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)

enum class NotificationType {
    EVENTO,
    SYNC,
    PROMOCION,
    RECORDATORIO
}