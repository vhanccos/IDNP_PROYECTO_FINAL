package com.example.myapplication001.domain.model

data class Event(
    val name: String,
    val date: String,
    val time: String,
    val location: String,
    val type: String
) {
    companion object {
        val sampleData = listOf(
            Event(
                name = "Noche de Museos",
                date = "Viernes, 29 Nov",
                time = "17:00 - 22:00",
                location = "Varios museos",
                type = "Cultural"
            ),
            Event(
                name = "Exposición de Arte Colonial",
                date = "Sábado, 30 Nov",
                time = "09:00 - 18:00",
                location = "Museo de Arte Virreinal Santa Teresa",
                type = "Religioso"
            )
        )
    }
}