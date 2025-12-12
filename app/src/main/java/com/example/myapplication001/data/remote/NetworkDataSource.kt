package com.example.myapplication001.data.remote

import com.example.myapplication001.data.local.entity.EventEntity
import com.example.myapplication001.data.local.entity.MuseumEntity
import com.example.myapplication001.data.local.entity.TripEntity
import com.example.myapplication001.data.local.entity.PhotoEntity

interface NetworkDataSource {
    suspend fun getMuseums(): List<MuseumEntity>
    suspend fun getEvents(): List<EventEntity>
    suspend fun getTrips(): List<TripEntity>
    suspend fun getPhotos(): List<PhotoEntity>
}
