
package com.example.myapplication001.domain.model

data class ActiveTourZone(
    val name: String,
    val imageUrl: String,
    val funFactTitle: String,
    val funFactText: String
) {
    companion object {
        val sample = ActiveTourZone(
            name = "Arcos de la Catedral",
            imageUrl = "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg",
            funFactTitle = "¿Sabías que?",
            funFactText = "La Catedral de Arequipa es uno de los primeros y más importantes monumentos religiosos del siglo XVII en la ciudad."
        )
    }
}

