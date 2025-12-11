package com.example.myapplication001.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication001.domain.model.Museum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
// IMPORTANTE: Usamos GeoPoint de OpenStreetMap
import org.osmdroid.util.GeoPoint

data class HomeUiState(
    val museums: List<Museum> = emptyList(),
    val isLoading: Boolean = false,
    val selectedMuseum: Museum? = null,
    val showMuseumDialog: Boolean = false
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // Coordenada Central (Plaza de Armas Arequipa)
    val arequipaCenterLocation = GeoPoint(-16.3988, -71.5369)

    init {
        loadMuseums()
    }

    private fun loadMuseums() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // AQUÍ ESTÁ LA CORRECCIÓN:
            // Agregamos la 'f' a los números y los campos que faltaban (imageUrl, funFactTitle, funFactText)
            val localMuseums = listOf(
                Museum(
                    id = "1",
                    name = "Museo Santa Teresa",
                    description = "Museo de arte virreinal en un convento del siglo XVIII.",
                    infoText = "Abierto - 9:00 AM",
                    ratingValue = 4.8f, // AGREGADA LA 'f' (Float)
                    ratingCount = 120,
                    imageUrl = "https://example.com/foto1.jpg", // CAMBIADO photoUrl POR imageUrl
                    funFactTitle = "¿Sabías qué?", // AGREGADO
                    funFactText = "Este museo fue un monasterio de clausura por más de 300 años." // AGREGADO
                ),
                Museum(
                    id = "2",
                    name = "Museo Santuarios Andinos",
                    description = "Hogar de la momia Juanita y artefactos incas.",
                    infoText = "Cerrado - Abre 10:00 AM",
                    ratingValue = 4.6f, // AGREGADA LA 'f'
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
                    ratingValue = 4.9f, // AGREGADA LA 'f'
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

    fun getMuseumLocation(museumId: String): GeoPoint {
        return when (museumId) {
            "1" -> GeoPoint(-16.3950, -71.5360)
            "2" -> GeoPoint(-16.4005, -71.5380)
            "3" -> GeoPoint(-16.3952, -71.5367)
            else -> arequipaCenterLocation
        }
    }
}
