package com.example.myapplication001.data.repository

import com.example.myapplication001.data.local.dao.PhotoDao
import com.example.myapplication001.data.local.dao.TripDao
import com.example.myapplication001.data.local.entity.PhotoEntity
import com.example.myapplication001.data.local.entity.TripEntity
import kotlinx.coroutines.flow.Flow

class UserDataRepository(
    private val tripDao: TripDao,
    private val photoDao: PhotoDao
) {
    // Local Data (User Generated) - Single Source of Truth is Room

    val trips: Flow<List<TripEntity>> = tripDao.getAllTrips()

    fun getPhotosByTrip(tripName: String): Flow<List<PhotoEntity>> {
        return photoDao.getPhotosByTrip(tripName)
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
