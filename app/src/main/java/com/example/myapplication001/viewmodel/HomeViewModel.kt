package com.example.myapplication001.view.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication001.model.Museum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
// IMPORTANTE: Aquí cambiamos a LatLng de Google Maps
import com.google.android.gms.maps.model.LatLng
import com.example.myapplication001.data.repository.MuseumRepository
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication001.MyApplication

data class HomeUiState(
    val museums: List<Museum> = emptyList(),
    val isLoading: Boolean = false,
    val selectedMuseum: Museum? = null,
    val showMuseumDialog: Boolean = false
)

class HomeViewModel(private val repository: MuseumRepository) : ViewModel() {

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
            
            // Trigger refresh from network (Mock or Real)
            repository.refreshMuseums()

            // Observe database
            repository.museums.collect { museums ->
                _uiState.value = _uiState.value.copy(
                    museums = museums.map { it.toDomain() }, // Need mapper
                    isLoading = false
                )
            }
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
        // Logic could be improved to use actual lat/long from entity
        val museum = _uiState.value.museums.find { it.id == museumId }
        return if (museum != null) {
             // For now, we don't have lat/long in Domain Museum, so we might need to update Domain model or Mapper
             // But wait, I added lat/long to Entity. I should update Domain model too.
             // For this step, I will keep the hardcoded logic OR update Domain model.
             // Let's update Domain model in next step. For now, keep hardcoded logic as fallback or simple map.
             when (museumId) {
                "1" -> LatLng(-16.3950, -71.5360) // Santa Teresa
                "2" -> LatLng(-16.4005, -71.5380) // Santuarios Andinos
                "3" -> LatLng(-16.3952, -71.5367) // Santa Catalina
                else -> arequipaCenterLocation
            }
        } else {
            arequipaCenterLocation
        }
    }
    
    // Mapper function (Temporary, should be in a Mapper class)
    private fun com.example.myapplication001.data.local.entity.MuseumEntity.toDomain(): Museum {
        return Museum(
            id = id,
            name = name,
            description = description,
            imageUrl = imageUrl,
            ratingValue = ratingValue,
            ratingCount = ratingCount,
            infoText = infoText,
            funFactTitle = funFactTitle,
            funFactText = funFactText
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val repository = (application as MyApplication).museumRepository
                return HomeViewModel(repository) as T
            }
        }
    }
}
