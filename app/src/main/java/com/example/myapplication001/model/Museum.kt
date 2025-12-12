package com.example.myapplication001.model

data class Museum(
    val id: String,
    val name: String,
    val imageUrl: String,
    val infoText: String,
    val ratingValue: Float,
    val ratingCount: Int,
    val description: String,
    val funFactTitle: String,
    val funFactText: String
)
