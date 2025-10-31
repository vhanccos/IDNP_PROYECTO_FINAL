package com.example.myapplication001.domain.model

data class Photo(
    val id: String,
    val tripName: String,
    val imageUrl: String,
    val timestamp: String,
    val timeHourStamp: String? = null
) {
    companion object {
        val sampleData = listOf(
            // Monasterio de Santa Catalina
            Photo("1", "Monasterio de Santa Catalina", "https://www.peru.travel/Contenido/General/Imagen/es/564/1.1/santa-catalina.jpg", "28 Nov", "10:30"),
            Photo("2", "Monasterio de Santa Catalina", "https://www.peru.travel/Contenido/Atractivo/Imagen/es/44/1.1/Principal/monasterio-santa-catalina.jpg", "28 Nov", "11:00"),
            Photo("3", "Monasterio de Santa Catalina", "https://blog.redbus.pe/wp-content/uploads/2020/03/117b8eaa1deb4d93d3d5c857585ed6ea.jpg", "28 Nov", "11:30"),

            // Mirador de Yanahuara
            Photo("4", "Mirador de Yanahuara", "https://www.peru.travel/Contenido/Atractivo/Imagen/es/42/1.1/Principal/mirador-yanahuara.jpg", "29 Nov, 09:00"),
            Photo("5", "Mirador de Yanahuara", "https://blog.incarail.com/wp-content/uploads/2025/07/mirador-de-yanahuara-arequipa.webp", "29 Nov, 09:30"),
            Photo("6", "Mirador de Yanahuara", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcThr5v7Xllp7PMAol8pB8Gx-vmmMzzQfppjKQ&s", "29 Nov, 10:00"),

            // Ruta del Sillar
            Photo("7", "Ruta del Sillar", "https://www.arequipa.com/wp-content/uploads/2020/09/canteras-de-sillar-arequipa.jpg", "30 Nov, 14:00"),
            Photo("8", "Ruta del Sillar", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS1EKtdx-8594SYi9BN2k8r8NOjWXOzi18p6w&s", "30 Nov, 14:30"),
            Photo("9", "Ruta del Sillar", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/14/b4/c2/d9/architectural-carvings.jpg?w=1200&h=1200&s=1", "30 Nov, 15:00"),

            // Cañón del Colca
            Photo("10", "Cañón del Colca", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpBxIYw2wE3dpDL_dfofHVdSS6ObJNVElPfw&s", "01 Dec, 08:00"),
            Photo("11", "Cañón del Colca", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmW8mSbWTlfYqceM5j0oDf9M1JWY4m5V99Ow&s", "01 Dec, 08:30"),
            Photo("12", "Cañón del Colca", "https://www.machupicchuterra.com/wp-content/uploads/canon-colca-10-media.jpg", "01 Dec, 09:00")
        )
    }
}