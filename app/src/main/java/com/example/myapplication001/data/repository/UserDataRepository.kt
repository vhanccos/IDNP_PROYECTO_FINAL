package com.example.myapplication001.data.repository

import com.example.myapplication001.data.local.dao.PhotoDao
import com.example.myapplication001.data.local.dao.TripDao
import com.example.myapplication001.data.local.entity.PhotoEntity
import com.example.myapplication001.data.local.entity.TripEntity
import kotlinx.coroutines.flow.Flow

import com.example.myapplication001.data.remote.MockNetworkDataSource
import com.example.myapplication001.data.remote.NetworkDataSource
import com.example.myapplication001.data.remote.RetrofitNetworkDataSource
import com.example.myapplication001.util.Constants

class UserDataRepository(
    private val tripDao: TripDao,
    private val photoDao: PhotoDao
) {
    private val networkDataSource: NetworkDataSource = if (Constants.IS_DEV_MODE) {
        MockNetworkDataSource()
    } else {
        RetrofitNetworkDataSource(Constants.BASE_URL)
    }

    // Local Data (User Generated) - Single Source of Truth is Room
    val trips: Flow<List<TripEntity>> = tripDao.getAllTrips()

    fun getPhotosByTrip(tripName: String): Flow<List<PhotoEntity>> {
        return photoDao.getPhotosByTrip(tripName)
    }

    fun getPhotoById(id: String): Flow<PhotoEntity?> {
        return photoDao.getPhotoById(id)
    }

    suspend fun refreshTrips() {
        try {
            val remoteTrips = networkDataSource.getTrips()
            // Here we might need a strategy to merge or just insert/update
            // For now, let's just insert new ones or ignore if exists (Dao strategy dependent)
            // Assuming insertTrip replaces or ignores conflicts. Let's check Dao later.
            // For simple sync:
            remoteTrips.forEach { tripDao.insertTrip(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun refreshPhotos() {
        try {
            val remotePhotos = networkDataSource.getPhotos()
            remotePhotos.forEach { photoDao.insertPhoto(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun createTrip(name: String, description: String, imageUrl: String) {
        val trip = TripEntity(name = name, description = description, imageUrl = imageUrl)
        tripDao.insertTrip(trip)
    }

    suspend fun deleteTrip(trip: TripEntity) {
        tripDao.deleteTrip(trip)
    }

    suspend fun addPhoto(tripName: String, imageUrl: String, timestamp: String, timeHourStamp: String?) {
        // ID generation strategy: UUID or simple timestamp for now
        val id = System.currentTimeMillis().toString()
        val photo = PhotoEntity(
            id = id,
            tripName = tripName,
            imageUrl = imageUrl,
            timestamp = timestamp,
            timeHourStamp = timeHourStamp
        )
        photoDao.insertPhoto(photo)
    }
}
