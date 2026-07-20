package com.example.myapplication001.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication001.model.NotificationType
import com.example.myapplication001.model.TuristearNotification
import com.example.myapplication001.util.NotificationHelper
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class NotificationsUiState(
    val notifications: List<TuristearNotification> = emptyList(),
    val fcmToken: String = "",
    val isLoading: Boolean = false,
    val permissionGranted: Boolean = false
)

class NotificationsViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("turistear_prefs", Context.MODE_PRIVATE)

    private val _uiState = MutableStateFlow(NotificationsUiState())
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    // Notificaciones de ejemplo que simularían venir de Room o FCM
    private val mockNotifications = listOf(
        TuristearNotification(
            title = "Noche de Museos",
            body = "Esta noche desde las 17:00 en el Centro Histórico de Arequipa. ¡Entrada libre!",
            type = NotificationType.EVENTO
        ),
        TuristearNotification(
            title = "Exposición Arte Colonial",
            body = "Mañana sábado en el Museo Santa Teresa. Obras del siglo XVIII.",
            type = NotificationType.EVENTO
        ),
        TuristearNotification(
            title = "Datos actualizados",
            body = "Los atractivos turísticos han sido actualizados con información reciente.",
            type = NotificationType.SYNC
        ),
        TuristearNotification(
            title = "Festival Gastronómico",
            body = "Este fin de semana en la Plaza de Armas. Platos típicos de Arequipa.",
            type = NotificationType.PROMOCION
        )
    )

    init {
        loadFcmToken()
        loadNotifications()
    }

    private fun loadNotifications() {
        _uiState.value = _uiState.value.copy(notifications = mockNotifications)
    }

    private fun loadFcmToken() {
        viewModelScope.launch {
            try {
                // Intentar obtener token guardado primero
                val savedToken = prefs.getString("fcm_token", "") ?: ""
                if (savedToken.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(fcmToken = savedToken)
                }
                // Refrescar desde Firebase
                val token = FirebaseMessaging.getInstance().token.await()
                prefs.edit().putString("fcm_token", token).apply()
                _uiState.value = _uiState.value.copy(fcmToken = token)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Enviar notificación local de prueba desde la UI
    fun sendTestNotification(context: Context) {
        viewModelScope.launch {
            NotificationHelper.showEventNotification(
                context = context,
                title = "¡Evento cerca de ti!",
                body = "El Festival de la Vendimia inicia mañana en el centro de Arequipa."
            )
            // Agregar a la lista local para que aparezca en pantalla
            val nueva = TuristearNotification(
                title = "¡Evento cerca de ti!",
                body = "El Festival de la Vendimia inicia mañana en el centro de Arequipa.",
                type = NotificationType.EVENTO
            )
            val actualizadas = listOf(nueva) + _uiState.value.notifications
            _uiState.value = _uiState.value.copy(notifications = actualizadas)
        }
    }

    fun markAsRead(notificationId: Int) {
        val actualizadas = _uiState.value.notifications.map { notif ->
            if (notif.id == notificationId) notif.copy(isRead = true) else notif
        }
        _uiState.value = _uiState.value.copy(notifications = actualizadas)
    }

    fun updatePermissionStatus(granted: Boolean) {
        _uiState.value = _uiState.value.copy(permissionGranted = granted)
    }
}