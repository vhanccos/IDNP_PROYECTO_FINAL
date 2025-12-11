package com.example.myapplication001.data.remote

import com.example.myapplication001.data.local.entity.EventEntity
import com.example.myapplication001.data.local.entity.MuseumEntity

interface NetworkDataSource {
    suspend fun getMuseums(): List<MuseumEntity>
    suspend fun getEvents(): List<EventEntity>
}
