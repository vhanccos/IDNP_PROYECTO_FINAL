package com.example.myapplication001.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector?) {
    object Home : Screen("home", "Inicio", Icons.Default.Home)
    object Events : Screen("events", "Eventos", Icons.Default.CalendarToday)
    object Profile : Screen("profile", "Perfil", Icons.Default.Person)
    object MuseumList : Screen("museum_list", "Lista de Museos", null)
    object MuseumDetail : Screen("museum_detail/{museumId}", "Detalle del Museo", null) {
        fun createRoute(museumId: String) = "museum_detail/$museumId"
    }
    object TripList : Screen("trip_list", "Mis Aventuras", null)
    object TripGallery : Screen("trip_gallery/{tripName}", "Galería de Viaje", null) {
        fun createRoute(tripName: String) = "trip_gallery/$tripName"
    }
    object GalleryDetail : Screen("gallery_detail/{photoId}", "Detalle de Foto", null) {
        fun createRoute(photoId: String) = "gallery_detail/$photoId"
    }
    object ActiveTour : Screen("active_tour", "Recorrido Activo", null)
}

val bottomNavigationItems = listOf(
    Screen.Events,
    Screen.Home,
    Screen.Profile
)