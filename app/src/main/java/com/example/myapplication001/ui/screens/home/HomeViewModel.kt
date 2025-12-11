package com.example.myapplication001.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication001.domain.model.Museum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
// IMPORTANTE: Aquí cambiamos a LatLng de Google Maps
import com.google.android.gms.maps.model.LatLng

data class HomeUiState(
    val museums: List<Museum> = emptyList(),
    val isLoading: Boolean = false,
    val selectedMuseum: Museum? = null,
    val showMuseumDialog: Boolean = false
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // CORRECCIÓN 1: Usamos LatLng en lugar de GeoPoint
    val arequipaCenterLocation = LatLng(-16.3988, -71.5369)

    init {
        loadMuseums()
    }

    private fun loadMuseums() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Datos quemados (Hardcoded)
            val localMuseums = listOf(
                Museum(
                    id = "1",
                    name = "Museo Santa Teresa",
                    description = "Museo de arte virreinal en un convento del siglo XVIII.",
                    infoText = "Abierto - 9:00 AM",
                    ratingValue = 4.8f,
                    ratingCount = 120,
                    imageUrl = "https://example.com/foto1.jpg",
                    funFactTitle = "¿Sabías qué?",
                    funFactText = "Este museo fue un monasterio de clausura por más de 300 años."
                ),
                Museum(
                    id = "2",
                    name = "Museo Santuarios Andinos",
                    description = "Hogar de la momia Juanita y artefactos incas.",
                    infoText = "Cerrado - Abre 10:00 AM",
                    ratingValue = 4.6f,
                    ratingCount = 340,
                    imageUrl = "https://example.com/foto2.jpg",
                    funFactTitle = "Dato Curioso",
                    funFactText = "La momia Juanita fue descubierta en el nevado Ampato."
                ),
                Museum(
                    id = "3",
                    name = "Monasterio Santa Catalina",
                    description = "Una ciudadela dentro de la ciudad, arquitectura colonial.",
                    infoText = "Abierto - 8:00 AM",
                    ratingValue = 4.9f,
                    ratingCount = 1500,
                    imageUrl = "https://example.com/foto3.jpg",
                    funFactTitle = "Historia",
                    funFactText = "Es considerado una pequeña ciudad dentro de Arequipa."
                )
            )

            _uiState.value = _uiState.value.copy(
                museums = localMuseums,
                isLoading = false
            )
        }
    }

    fun onMuseumClick(museum: Museum) {
        _uiState.value = _uiState.value.copy(
            selectedMuseum = museum,
            showMuseumDialog = true
        )
    }

    fun dismissMuseumDialog() {
        _uiState.value = _uiState.value.copy(
            selectedMuseum = null,
            showMuseumDialog = false
        )
    }

    // CORRECCIÓN 2: La función devuelve LatLng
    fun getMuseumLocation(museumId: String): LatLng {
        return when (museumId) {
            "1" -> LatLng(-16.3950, -71.5360) // Santa Teresa
            "2" -> LatLng(-16.4005, -71.5380) // Santuarios Andinos
            "3" -> LatLng(-16.3952, -71.5367) // Santa Catalina
            else -> arequipaCenterLocation
        }
    }
}
