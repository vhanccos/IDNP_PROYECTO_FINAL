
package com.example.myapplication001.domain.model

data class Photo(
    val id: String,
    val imageUrl: String,
    val timestamp: String
) {
    companion object {
        val sampleData = listOf(
            Photo("1", "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg", "28 Nov, 10:30"),
            Photo("2", "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg", "28 Nov, 11:00"),
            Photo("3", "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg", "28 Nov, 11:30"),
            Photo("4", "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg", "28 Nov, 12:00"),
            Photo("5", "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg", "28 Nov, 12:30"),
            Photo("6", "https://static.vecteezy.com/system/resources/previews/020/936/424/non_2x/wireframe-icon-for-your-website-design-logo-app-ui-free-vector.jpg", "28 Nov, 13:00"),
        )
    }
}

