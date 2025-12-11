package com.example.myapplication001.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

import androidx.room.Index

@Entity(tableName = "trips", indices = [Index(value = ["name"], unique = true)])
data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val imageUrl: String
)
