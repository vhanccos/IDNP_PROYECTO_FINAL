package com.example.myapplication001.domain.model

data class Trip(
    val name: String,
    val imageUrl: String,
    val description: String
) {
    companion object {
        val sampleData = listOf(
            Trip(
                name = "Monasterio de Santa Catalina",
                imageUrl = "https://www.peru.travel/Contenido/General/Imagen/es/564/1.1/santa-catalina.jpg",
                description = "Una ciudadela de sillar en el corazón de Arequipa."
            ),
            Trip(
                name = "Mirador de Yanahuara",
                imageUrl = "https://www.peru.travel/Contenido/Atractivo/Imagen/es/42/1.1/Principal/mirador-yanahuara.jpg",
                description = "Vistas espectaculares de la ciudad y el Misti."
            ),
            Trip(
                name = "Ruta del Sillar",
                imageUrl = "https://www.arequipa.com/wp-content/uploads/2020/09/canteras-de-sillar-arequipa.jpg",
                description = "Canteras de donde se extrae la piedra volcánica blanca."
            ),
            Trip(
                name = "Cañón del Colca",
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpBxIYw2wE3dpDL_dfofHVdSS6ObJNVElPfw&s",
                description = "Uno de los cañones más profundos del mundo."
            )
        )
    }
}
