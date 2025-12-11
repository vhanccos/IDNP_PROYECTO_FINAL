package com.example.myapplication001.data.remote

import com.example.myapplication001.data.local.entity.EventEntity
import com.example.myapplication001.data.local.entity.MuseumEntity
import kotlinx.coroutines.delay

class MockNetworkDataSource : NetworkDataSource {
    override suspend fun getMuseums(): List<MuseumEntity> {
        delay(1000) // Simulate network delay
        return listOf(
            MuseumEntity(
                id = "1",
                name = "Museo Santa Teresa",
                description = "Museo de arte virreinal en un convento del siglo XVIII.",
                imageUrl = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0b/69/f6/04/1.jpg",
                ratingValue = 4.8f,
                ratingCount = 120,
                infoText = "Abierto - 9:00 AM",
                funFactTitle = "¿Sabías qué?",
                funFactText = "Este museo fue un monasterio de clausura por más de 300 años.",
                latitude = -16.3950,
                longitude = -71.5360
            ),
            MuseumEntity(
                id = "2",
                name = "Museo Santuarios Andinos",
                description = "Hogar de la momia Juanita y artefactos incas.",
                imageUrl = "https://lh3.googleusercontent.com/gps-cs-s/AG0ilSyQtsPQE8thtVtQ1AkHPXgRPC4b5VAYOc13UYJ9i9-nmgpT0kMeuZhP2yPaxFgrcjVCmtAUiGq7bxH2YMSNYLfDPQ_CdRMWeblQpyUj6upFbZuIhks-ZY3E1l2Xy9cxUWbXc3WWDQ=w270-h312-n-k-no",
                ratingValue = 4.6f,
                ratingCount = 340,
                infoText = "Cerrado - Abre 10:00 AM",
                funFactTitle = "Dato Curioso",
                funFactText = "La momia Juanita fue descubierta en el nevado Ampato.",
                latitude = -16.4005,
                longitude = -71.5380
            ),
            MuseumEntity(
                id = "3",
                name = "Monasterio Santa Catalina",
                description = "Una ciudadela dentro de la ciudad, arquitectura colonial.",
                imageUrl = "https://www.peru.travel/Contenido/General/Imagen/es/564/1.1/santa-catalina.jpg",
                ratingValue = 4.9f,
                ratingCount = 1500,
                infoText = "Abierto - 8:00 AM",
                funFactTitle = "Historia",
                funFactText = "Es considerado una pequeña ciudad dentro de Arequipa.",
                latitude = -16.3952,
                longitude = -71.5367
            )
        )
    }

    override suspend fun getEvents(): List<EventEntity> {
        delay(500)
        return listOf(
            EventEntity(
                name = "Noche de Museos",
                date = "Viernes, 29 Nov",
                time = "17:00 - 22:00",
                location = "Varios museos",
                type = "Cultural"
            ),
            EventEntity(
                name = "Exposición de Arte Colonial",
                date = "Sábado, 30 Nov",
                time = "09:00 - 18:00",
                location = "Museo de Arte Virreinal Santa Teresa",
                type = "Religioso"
            )
        )
    }
}
