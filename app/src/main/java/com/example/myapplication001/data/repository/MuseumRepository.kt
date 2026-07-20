package com.example.myapplication001.data.repository

import com.example.myapplication001.data.local.dao.EventDao
import com.example.myapplication001.data.local.dao.MuseumDao
import com.example.myapplication001.data.local.entity.EventEntity
import com.example.myapplication001.data.local.entity.MuseumEntity
import com.example.myapplication001.data.remote.MockNetworkDataSource
import com.example.myapplication001.data.remote.NetworkDataSource
import com.example.myapplication001.data.remote.RetrofitNetworkDataSource
import com.example.myapplication001.util.Constants
import com.example.myapplication001.util.NetworkMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MuseumRepository(
    private val museumDao: MuseumDao,
    private val eventDao: EventDao,
    val networkMonitor: NetworkMonitor          // ← agregado
) {
    private val networkDataSource: NetworkDataSource = if (Constants.IS_DEV_MODE) {
        MockNetworkDataSource()
    } else {
        RetrofitNetworkDataSource(Constants.BASE_URL)
    }

    // Single Source of Truth — sin cambios
    val museums: Flow<List<MuseumEntity>> = museumDao.getAllMuseums()
    val events: Flow<List<EventEntity>> = eventDao.getAllEvents()

    // Estado de sincronización — nuevo
    private val _syncResult = MutableStateFlow<SyncResult>(SyncResult.Idle)
    val syncResult: StateFlow<SyncResult> = _syncResult.asStateFlow()

    // Reemplaza refreshMuseums() y refreshEvents() con uno solo
    suspend fun syncAll() {
        _syncResult.value = SyncResult.Syncing
        try {
            museumDao.insertAll(networkDataSource.getMuseums())
            eventDao.insertAll(networkDataSource.getEvents())
            _syncResult.value = SyncResult.Success
        } catch (e: Exception) {
            e.printStackTrace()
            _syncResult.value = SyncResult.Error(e.localizedMessage ?: "Sin conexión")
        }
    }

    // Conservados por compatibilidad con el resto de la app
    suspend fun refreshMuseums() {
        try {
            museumDao.insertAll(networkDataSource.getMuseums())
        } catch (e: Exception) { e.printStackTrace() }
    }

    suspend fun refreshEvents() {
        try {
            eventDao.insertAll(networkDataSource.getEvents())
        } catch (e: Exception) { e.printStackTrace() }
    }

    fun getMuseumById(id: String): Flow<MuseumEntity?> = museumDao.getMuseumById(id)
}