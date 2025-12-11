package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication001.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GalleryDetailUiState(
    val photo: Photo? = null,
    val isLoading: Boolean = false
)

class GalleryDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GalleryDetailUiState())
    val uiState: StateFlow<GalleryDetailUiState> = _uiState.asStateFlow()

    fun loadPhoto(photoId: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val photo = Photo.sampleData.find { it.id == photoId }
            _uiState.value = _uiState.value.copy(
                photo = photo,
                isLoading = false
            )
        }
    }
}
