package com.example.myapplication001.model

data class Photo(
    val id: String,
    val tripName: String,
    val imageUrl: String,
    val timestamp: String,
    val timeHourStamp: String? = null
)