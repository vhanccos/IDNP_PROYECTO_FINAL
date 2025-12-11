package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.myapplication001.model.Photo

data class TripGalleryUiState(
    val isLoading: Boolean = false,
    val photos: List<Photo> = emptyList()
)

class TripGalleryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TripGalleryUiState())
    val uiState: StateFlow<TripGalleryUiState> = _uiState.asStateFlow()
}
