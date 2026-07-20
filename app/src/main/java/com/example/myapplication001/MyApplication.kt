package com.example.myapplication001

import android.app.Application
import com.example.myapplication001.data.local.AppDatabase
import com.example.myapplication001.data.repository.MuseumRepository
import com.example.myapplication001.data.repository.UserDataRepository
import com.example.myapplication001.util.NotificationHelper
import com.example.myapplication001.util.NetworkMonitor
import com.example.myapplication001.util.WorkManagerScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MyApplication : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val networkMonitor by lazy { NetworkMonitor(this) }

    val museumRepository by lazy {
        MuseumRepository(
            museumDao = database.museumDao(),
            eventDao = database.eventDao(),
            networkMonitor = networkMonitor
        )
    }

    val userDataRepository by lazy {
        UserDataRepository(database.tripDao(), database.photoDao())
    }

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        // 1. Canales de notificación (requerido antes de cualquier notify)
        NotificationHelper.createNotificationChannels(this)

        // 2. Programar sync periódico con WorkManager (cada 12h con red)
        WorkManagerScheduler.schedulePeriodic(this)

        // 3. Sync inicial al abrir la app (si hay red, Room se actualiza;
        //    si no hay red, Room ya tiene datos del último WorkManager run)
        applicationScope.launch {
            try {
                museumRepository.syncAll()
                userDataRepository.refreshTrips()
                userDataRepository.refreshPhotos()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}