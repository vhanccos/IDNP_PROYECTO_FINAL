package com.example.myapplication001.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "photos",
    foreignKeys = [
        ForeignKey(
            entity = TripEntity::class,
            parentColumns = ["name"],
            childColumns = ["tripName"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["tripName"])]
)
data class PhotoEntity(
    @PrimaryKey val id: String,
    val tripName: String,
    val imageUrl: String,
    val timestamp: String,
    val timeHourStamp: String?
)
