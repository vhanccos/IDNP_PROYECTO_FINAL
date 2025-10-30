package com.example.myapplication001.domain.model

data class Museum(
    val id: String,
    val name: String,
    val imageUrl: String,
    val infoText: String,
    val ratingValue: Float,
    val ratingCount: Int,
    val description: String
) {
    companion object {
        val sampleData = listOf(
            Museum(
                id = "1",
                name = "Museo de Arte Virreinal Santa Teresa",
                imageUrl = "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg",
                infoText = "Horario - Abierto",
                ratingValue = 4.5f,
                ratingCount = 574,
                description = "El Monasterio de Carmelitas Descalzas de San José y Santa Teresa de Arequipa, fue fundado el 23 de noviembre de 1710. Desde esa fecha, ha permanecido habitado por una Comunidad de Monjas de Clausura, y ha atesorado por más de 300 años, invalorables colecciones de pintura, escultura, orfebrería, mobiliario, y objetos de uso cotidiano de gran valor histórico y artístico, que se conservan hasta hoy y que se exhiben en sus 13 salas de exposición."
            ),
            Museum(
                id = "2",
                name = "Museo Santuarios Andinos",
                imageUrl = "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg",
                infoText = "Horario - Cerrado",
                ratingValue = 4.8f,
                ratingCount = 1234,
                description = "El Museo Santuarios Andinos de la Universidad Católica de Santa María, fue creado para albergar y exhibir los hallazgos de las investigaciones del Proyecto Santuarios de Altura del Sur Andino, dirigido por el Dr. Johan Reinhard y el arqueólogo José Antonio Chávez. El principal hallazgo es la Dama del Ampato, conocida como la Momia Juanita."
            )
        )
    }
}