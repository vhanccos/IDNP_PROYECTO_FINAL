package com.example.myapplication001.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myapplication001.MyApplication
import com.example.myapplication001.util.NotificationHelper

class SyncWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val TAG = "SyncWorker"
        const val WORK_NAME = "turistear_sync_periodico"
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "Iniciando sincronización periódica en background...")

        return try {
            val repository = (context.applicationContext as MyApplication).museumRepository
            val userDataRepository = (context.applicationContext as MyApplication).userDataRepository

            // Sincronizar museos y eventos
            repository.syncAll()

            // Sincronizar trips y fotos
            userDataRepository.refreshTrips()
            userDataRepository.refreshPhotos()

            Log.d(TAG, "Sincronización completada exitosamente")

            // Notificar al usuario que los datos están actualizados
            NotificationHelper.showSyncNotification(
                context = context,
                message = "Los atractivos turísticos de Arequipa han sido actualizados."
            )

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error en sincronización: ${e.localizedMessage}")
            // Retry automático si falla (máximo 3 intentos por defecto)
            Result.retry()
        }
    }
}