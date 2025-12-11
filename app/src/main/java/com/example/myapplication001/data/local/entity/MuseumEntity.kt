package com.example.myapplication001.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "museums")
data class MuseumEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val ratingValue: Float,
    val ratingCount: Int,
    val infoText: String,
    val funFactTitle: String,
    val funFactText: String,
    val latitude: Double,
    val longitude: Double
)
