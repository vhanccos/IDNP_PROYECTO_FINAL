package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.myapplication001.model.Photo

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.myapplication001.data.repository.UserDataRepository
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication001.MyApplication

data class TripGalleryUiState(
    val isLoading: Boolean = false,
    val photos: List<Photo> = emptyList()
)

class TripGalleryViewModel(private val repository: UserDataRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(TripGalleryUiState())
    val uiState: StateFlow<TripGalleryUiState> = _uiState.asStateFlow()

    fun loadPhotos(tripName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // Trigger refresh logic if needed, but for now just observe
            // Since photos might come from API in future, we might call refreshPhotos() here if it was by trip,
            // but refreshPhotos() currently gets ALL photos. Let's assume we just observe for now.
            // Actually, we should try to refresh if list is empty.
            if (_uiState.value.photos.isEmpty()) {
                 repository.refreshPhotos() 
            }

            repository.getPhotosByTrip(tripName).collect { entities ->
                val photos = entities.map { entity ->
                    Photo(
                        id = entity.id,
                        tripName = entity.tripName,
                        imageUrl = entity.imageUrl,
                        timestamp = entity.timestamp,
                        timeHourStamp = entity.timeHourStamp
                    )
                }
                _uiState.value = _uiState.value.copy(
                    photos = photos,
                    isLoading = false
                )
            }
        }
    }
    
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val repository = (application as MyApplication).userDataRepository
                return TripGalleryViewModel(repository) as T
            }
        }
    }
}
