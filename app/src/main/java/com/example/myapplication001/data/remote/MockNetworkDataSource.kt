package com.example.myapplication001.data.remote

import com.example.myapplication001.data.local.entity.EventEntity
import com.example.myapplication001.data.local.entity.MuseumEntity
import com.example.myapplication001.data.local.entity.TripEntity
import com.example.myapplication001.data.local.entity.PhotoEntity
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

    override suspend fun getTrips(): List<TripEntity> {
        delay(500)
        return listOf(
            TripEntity(
                name = "Monasterio de Santa Catalina",
                imageUrl = "https://www.peru.travel/Contenido/General/Imagen/es/564/1.1/santa-catalina.jpg",
                description = "Una ciudadela de sillar en el corazón de Arequipa."
            ),
            TripEntity(
                name = "Mirador de Yanahuara",
                imageUrl = "https://www.peru.travel/Contenido/Atractivo/Imagen/es/42/1.1/Principal/mirador-yanahuara.jpg",
                description = "Vistas espectaculares de la ciudad y el Misti."
            ),
            TripEntity(
                name = "Ruta del Sillar",
                imageUrl = "https://www.arequipa.com/wp-content/uploads/2020/09/canteras-de-sillar-arequipa.jpg",
                description = "Canteras de donde se extrae la piedra volcánica blanca."
            ),
            TripEntity(
                name = "Cañón del Colca",
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpBxIYw2wE3dpDL_dfofHVdSS6ObJNVElPfw&s",
                description = "Uno de los cañones más profundos del mundo."
            )
        )
    }

    override suspend fun getPhotos(): List<PhotoEntity> {
        delay(500)
        return listOf(
            // Monasterio de Santa Catalina
            PhotoEntity("1", "Monasterio de Santa Catalina", "https://www.peru.travel/Contenido/General/Imagen/es/564/1.1/santa-catalina.jpg", "28 Nov", "10:30"),
            PhotoEntity("2", "Monasterio de Santa Catalina", "https://www.peru.travel/Contenido/Atractivo/Imagen/es/44/1.1/Principal/monasterio-santa-catalina.jpg", "28 Nov", "11:00"),
            PhotoEntity("3", "Monasterio de Santa Catalina", "https://blog.redbus.pe/wp-content/uploads/2020/03/117b8eaa1deb4d93d3d5c857585ed6ea.jpg", "28 Nov", "11:30"),

            // Mirador de Yanahuara
            PhotoEntity("4", "Mirador de Yanahuara", "https://www.peru.travel/Contenido/Atractivo/Imagen/es/42/1.1/Principal/mirador-yanahuara.jpg", "29 Nov", "09:00"),
            PhotoEntity("5", "Mirador de Yanahuara", "https://blog.incarail.com/wp-content/uploads/2025/07/mirador-de-yanahuara-arequipa.webp", "29 Nov", "09:30"),
            PhotoEntity("6", "Mirador de Yanahuara", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcThr5v7Xllp7PMAol8pB8Gx-vmmMzzQfppjKQ&s", "29 Nov", "10:00"),

            // Ruta del Sillar
            PhotoEntity("7", "Ruta del Sillar", "https://www.arequipa.com/wp-content/uploads/2020/09/canteras-de-sillar-arequipa.jpg", "30 Nov", "14:00"),
            PhotoEntity("8", "Ruta del Sillar", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS1EKtdx-8594SYi9BN2k8r8NOjWXOzi18p6w&s", "30 Nov", "14:30"),
            PhotoEntity("9", "Ruta del Sillar", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/14/b4/c2/d9/architectural-carvings.jpg?w=1200&h=1200&s=1", "30 Nov", "15:00"),

            // Cañón del Colca
            PhotoEntity("10", "Cañón del Colca", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpBxIYw2wE3dpDL_dfofHVdSS6ObJNVElPfw&s", "01 Dec", "08:00"),
            PhotoEntity("11", "Cañón del Colca", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmW8mSbWTlfYqceM5j0oDf9M1JWY4m5V99Ow&s", "01 Dec", "08:30"),
            PhotoEntity("12", "Cañón del Colca", "https://www.machupicchuterra.com/wp-content/uploads/canon-colca-10-media.jpg", "01 Dec", "09:00")
        )
    }
}
