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
                imageUrl = "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg",
                description = "Una ciudadela de sillar en el corazón de Arequipa."
            ),
            Trip(
                name = "Mirador de Yanahuara",
                imageUrl = "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg",
                description = "Vistas espectaculares de la ciudad y el Misti."
            ),
            Trip(
                name = "Ruta del Sillar",
                imageUrl = "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg",
                description = "Canteras de donde se extrae la piedra volcánica blanca."
            ),
            Trip(
                name = "Cañón del Colca",
                imageUrl = "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg",
                description = "Uno de los cañones más profundos del mundo."
            )
        )
    }
}
