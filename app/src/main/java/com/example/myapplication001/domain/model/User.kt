package com.example.myapplication001.domain.model

data class User(
    val name: String,
    val avatarUrl: String,
    val placesVisited: Int,
    val totalVisits: Int
) {
    companion object {
        val sample = User(
            name = "Vlad",
            avatarUrl = "",
            placesVisited = 12,
            totalVisits = 45
        )
    }
}