package com.example.myapplication001.data.repository

import com.example.myapplication001.data.local.dao.EventDao
import com.example.myapplication001.data.local.dao.MuseumDao
import com.example.myapplication001.data.local.entity.EventEntity
import com.example.myapplication001.data.local.entity.MuseumEntity
import com.example.myapplication001.data.remote.MockNetworkDataSource
import com.example.myapplication001.data.remote.NetworkDataSource
import com.example.myapplication001.data.remote.RetrofitNetworkDataSource
import com.example.myapplication001.util.Constants
import kotlinx.coroutines.flow.Flow

class MuseumRepository(
    private val museumDao: MuseumDao,
    private val eventDao: EventDao
) {
    private val networkDataSource: NetworkDataSource = if (Constants.IS_DEV_MODE) {
        MockNetworkDataSource()
    } else {
        RetrofitNetworkDataSource(Constants.BASE_URL)
    }

    // Single Source of Truth: UI always observes Database
    val museums: Flow<List<MuseumEntity>> = museumDao.getAllMuseums()
    val events: Flow<List<EventEntity>> = eventDao.getAllEvents()

    suspend fun refreshMuseums() {
        try {
            val remoteMuseums = networkDataSource.getMuseums()
            museumDao.insertAll(remoteMuseums)
        } catch (e: Exception) {
            e.printStackTrace()
            // In a real app, handle error (e.g., emit to a SharedFlow for UI error message)
        }
    }

    suspend fun refreshEvents() {
        try {
            val remoteEvents = networkDataSource.getEvents()
            eventDao.insertAll(remoteEvents)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun getMuseumById(id: String): Flow<MuseumEntity?> {
        return museumDao.getMuseumById(id)
    }
}
