package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication001.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TripGalleryUiState(
    val photos: List<Photo> = emptyList(),
    val isLoading: Boolean = false
)

class TripGalleryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TripGalleryUiState())
    val uiState: StateFlow<TripGalleryUiState> = _uiState.asStateFlow()

    fun loadPhotos(tripName: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val filteredPhotos = Photo.sampleData.filter { it.tripName == tripName }
            _uiState.value = _uiState.value.copy(
                photos = filteredPhotos,
                isLoading = false
            )
        }
    }
}
