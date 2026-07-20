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
                imageUrl = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0b/05/38/d9/img-20160424-103448-largejpg.jpg",
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
                name = "Centro Histórico",
                imageUrl = "https://www.peru.travel/Contenido/Uploads/Convento%20de%20la%20Compa%C3%B1%C3%ADa%20de%20Jes%C3%BAs_637717312195957959.jpg",
                description = "Patrimonio Cultural de la Humanidad por UNESCO"
            ),
            TripEntity(
                name = "Mercado San Camilo",
                imageUrl = "https://diariocorreo.pe/resizer/-__RX30YX0tmT4HccVD0axt65kk=/1200x1200/smart/filters:format(jpeg):quality(75)/arc-anglerfish-arc2-prod-elcomercio.s3.amazonaws.com/public/3XXLAJ5DIBFZ3BID6UR5BROOOM.jpg",
                description = "Mercado tradicional arequipeño con productos locales"
            ),
            TripEntity(
                name = "Plaza de Armas",
                imageUrl = "https://www.amarujourneyperu.com/blog/wp-content/uploads/plazaarequipa1.webp",
                description = "Corazón de la ciudad blanca rodeada de portales"
            ),
            TripEntity(
                name = "Volcán Misti",
                imageUrl = "https://media.traveler.es/photos/61376ddacb06ad0f20e12713/master/pass/143246.jpg",
                description = "Volcán emblemático visible desde toda la ciudad"
            ),
            TripEntity(
                name = "Mirador de Yanahuara",
                imageUrl = "https://www.peru.travel/Contenido/Atractivo/Imagen/es/42/1.1/Principal/mirador-yanahuara.jpg",
                description = "Vistas espectaculares de la ciudad y el Misti"
            ),
            TripEntity(
                name = "Monasterio de Santa Catalina",
                imageUrl = "https://www.peru.travel/Contenido/General/Imagen/es/564/1.1/santa-catalina.jpg",
                description = "Una ciudadela de sillar en el corazón de Arequipa"
            )
        )
    }

    override suspend fun getPhotos(): List<PhotoEntity> {
        delay(500)
        return listOf(
            // ========== CENTRO HISTÓRICO (6 fotos) ==========
            PhotoEntity(
                "1",
                "Centro Histórico",
                "https://www.peru.travel/Contenido/Destino/Imagen/es/9/1.2/Principal/centro-historico-arequipa.jpg",
                "25 Nov",
                "10:00"
            ),
            PhotoEntity(
                "2",
                "Centro Histórico",
                "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2022/02/10012609/Centro-Historico-de-Arequipa.jpg",
                "25 Nov",
                "10:30"
            ),
            PhotoEntity(
                "3",
                "Centro Histórico",
                "https://www.peru.travel/Contenido/General/Imagen/es/564/1.1/santa-catalina.jpg",
                "25 Nov",
                "11:00"
            ),
            PhotoEntity(
                "19",
                "Centro Histórico",
                "https://www.peru.travel/Contenido/Uploads/Convento%20de%20la%20Compa%C3%B1%C3%ADa%20de%20Jes%C3%BAs_637717312195957959.jpg",
                "25 Nov",
                "11:30"
            ),
            PhotoEntity(
                "20",
                "Centro Histórico",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/Arequipa_-_Peru.jpg/1200px-Arequipa_-_Peru.jpg",
                "25 Nov",
                "12:00"
            ),
            PhotoEntity(
                "21",
                "Centro Histórico",
                "https://www.peru.travel/Contenido/Atractivo/Imagen/es/43/1.1/Principal/iglesia-compania-jesus.jpg",
                "25 Nov",
                "12:30"
            ),

            // ========== MERCADO SAN CAMILO (6 fotos) ==========
            PhotoEntity(
                "4",
                "Mercado San Camilo",
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0f/3c/9e/7d/mercado-san-camilo.jpg",
                "26 Nov",
                "08:00"
            ),
            PhotoEntity(
                "5",
                "Mercado San Camilo",
                "https://d37rmf1ynyg9aw.cloudfront.net/fit-in/1280x1280/data/v4/resources/images/6d374e0e-2d27-4e62-bfad-a0ae3adaaab8.jpg",
                "26 Nov",
                "08:30"
            ),
            PhotoEntity(
                "6",
                "Mercado San Camilo",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRnXLj1lhPmE-Gj8WZqCBOgCKrYLiCHQzVIw&s",
                "26 Nov",
                "09:00"
            ),
            PhotoEntity(
                "22",
                "Mercado San Camilo",
                "https://diariocorreo.pe/resizer/-__RX30YX0tmT4HccVD0axt65kk=/1200x1200/smart/filters:format(jpeg):quality(75)/arc-anglerfish-arc2-prod-elcomercio.s3.amazonaws.com/public/3XXLAJ5DIBFZ3BID6UR5BROOOM.jpg",
                "26 Nov",
                "09:30"
            ),
            PhotoEntity(
                "23",
                "Mercado San Camilo",
                "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2022/02/10012609/Mercado-San-Camilo-Arequipa.jpg",
                "26 Nov",
                "10:00"
            ),
            PhotoEntity(
                "24",
                "Mercado San Camilo",
                "https://www.peru.travel/Contenido/Atractivo/Imagen/es/539/1.1/Principal/mercado-san-camilo.jpg",
                "26 Nov",
                "10:30"
            ),

            // ========== PLAZA DE ARMAS (6 fotos) ==========
            PhotoEntity(
                "7",
                "Plaza de Armas",
                "https://www.peru.travel/Contenido/Atractivo/Imagen/es/41/1.1/Principal/plaza-armas-arequipa.jpg",
                "27 Nov",
                "16:00"
            ),
            PhotoEntity(
                "8",
                "Plaza de Armas",
                "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2022/02/10012609/Plaza-de-Armas-de-Arequipa.jpg",
                "27 Nov",
                "16:30"
            ),
            PhotoEntity(
                "9",
                "Plaza de Armas",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQd6Z3vRfPPt2J2gqLhX1V9T_TwQcJZKqHjYA&s",
                "27 Nov",
                "17:00"
            ),
            PhotoEntity(
                "25",
                "Plaza de Armas",
                "https://www.amarujourneyperu.com/blog/wp-content/uploads/plazaarequipa1.webp",
                "27 Nov",
                "17:30"
            ),
            PhotoEntity(
                "26",
                "Plaza de Armas",
                "https://www.peru.travel/Contenido/General/Imagen/es/41/1.1/plaza-de-armas-noche.jpg",
                "27 Nov",
                "18:00"
            ),
            PhotoEntity(
                "27",
                "Plaza de Armas",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ab/Plaza_de_Armas%2C_Arequipa%2C_Per%C3%BA%2C_2015-08-02%2C_DD_49.JPG/1200px-Plaza_de_Armas%2C_Arequipa%2C_Per%C3%BA%2C_2015-08-02%2C_DD_49.JPG",
                "27 Nov",
                "18:30"
            ),

            // ========== VOLCÁN MISTI (6 fotos) ==========
            PhotoEntity(
                "10",
                "Volcán Misti",
                "https://www.peru.travel/Contenido/Atractivo/Imagen/es/569/1.1/Principal/volcan-misti.jpg",
                "28 Nov",
                "07:00"
            ),
            PhotoEntity(
                "11",
                "Volcán Misti",
                "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2022/02/10012609/Volcan-Misti-Arequipa.jpg",
                "28 Nov",
                "07:30"
            ),
            PhotoEntity(
                "12",
                "Volcán Misti",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZwGzh5MUGOg0Ykx-KhIABMYCwYKlzFBbfRQ&s",
                "28 Nov",
                "08:00"
            ),
            PhotoEntity(
                "28",
                "Volcán Misti",
                "https://media.traveler.es/photos/61376ddacb06ad0f20e12713/master/pass/143246.jpg",
                "28 Nov",
                "08:30"
            ),
            PhotoEntity(
                "29",
                "Volcán Misti",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1e/Misti_volcano_arequipa.jpg/1200px-Misti_volcano_arequipa.jpg",
                "28 Nov",
                "09:00"
            ),
            PhotoEntity(
                "30",
                "Volcán Misti",
                "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2022/02/10012609/Volcan-Misti-vista.jpg",
                "28 Nov",
                "09:30"
            ),

            // ========== MIRADOR DE YANAHUARA (6 fotos) ==========
            PhotoEntity(
                "13",
                "Mirador de Yanahuara",
                "https://www.peru.travel/Contenido/Atractivo/Imagen/es/42/1.1/Principal/mirador-yanahuara.jpg",
                "29 Nov",
                "09:00"
            ),
            PhotoEntity(
                "14",
                "Mirador de Yanahuara",
                "https://blog.incarail.com/wp-content/uploads/2025/07/mirador-de-yanahuara-arequipa.webp",
                "29 Nov",
                "09:30"
            ),
            PhotoEntity(
                "15",
                "Mirador de Yanahuara",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcThr5v7Xllp7PMAol8pB8Gx-vmmMzzQfppjKQ&s",
                "29 Nov",
                "10:00"
            ),
            PhotoEntity(
                "31",
                "Mirador de Yanahuara",
                "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2022/02/10012609/Mirador-de-Yanahuara.jpg",
                "29 Nov",
                "10:30"
            ),
            PhotoEntity(
                "32",
                "Mirador de Yanahuara",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0f/Mirador_de_Yanahuara%2C_Arequipa%2C_Per%C3%BA%2C_2015-08-02%2C_DD_55.JPG/1200px-Mirador_de_Yanahuara%2C_Arequipa%2C_Per%C3%BA%2C_2015-08-02%2C_DD_55.JPG",
                "29 Nov",
                "11:00"
            ),
            PhotoEntity(
                "33",
                "Mirador de Yanahuara",
                "https://www.peru.travel/Contenido/Atractivo/Imagen/es/42/1.2/mirador-yanahuara-arcos.jpg",
                "29 Nov",
                "11:30"
            ),

            // ========== MONASTERIO DE SANTA CATALINA (6 fotos) ==========
            PhotoEntity(
                "16",
                "Monasterio de Santa Catalina",
                "https://www.peru.travel/Contenido/General/Imagen/es/564/1.1/santa-catalina.jpg",
                "30 Nov",
                "10:30"
            ),
            PhotoEntity(
                "17",
                "Monasterio de Santa Catalina",
                "https://www.peru.travel/Contenido/Atractivo/Imagen/es/44/1.1/Principal/monasterio-santa-catalina.jpg",
                "30 Nov",
                "11:00"
            ),
            PhotoEntity(
                "18",
                "Monasterio de Santa Catalina",
                "https://blog.redbus.pe/wp-content/uploads/2020/03/117b8eaa1deb4d93d3d5c857585ed6ea.jpg",
                "30 Nov",
                "11:30"
            ),
            PhotoEntity(
                "34",
                "Monasterio de Santa Catalina",
                "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2022/02/10012609/Monasterio-Santa-Catalina.jpg",
                "30 Nov",
                "12:00"
            ),
            PhotoEntity(
                "35",
                "Monasterio de Santa Catalina",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/Santa_Catalina_Monastery_-_Arequipa_-_Peru.jpg/1200px-Santa_Catalina_Monastery_-_Arequipa_-_Peru.jpg",
                "30 Nov",
                "12:30"
            ),
            PhotoEntity(
                "36",
                "Monasterio de Santa Catalina",
                "https://www.peru.travel/Contenido/Atractivo/Imagen/es/44/1.2/monasterio-santa-catalina-interior.jpg",
                "30 Nov",
                "13:00"
            )
        )
    }
}