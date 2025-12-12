package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.myapplication001.data.repository.UserDataRepository
import com.example.myapplication001.model.Photo
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication001.MyApplication

data class GalleryDetailUiState(
    val isLoading: Boolean = false,
    val photo: Photo? = null
)

class GalleryDetailViewModel(private val repository: UserDataRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(GalleryDetailUiState())
    val uiState: StateFlow<GalleryDetailUiState> = _uiState.asStateFlow()

    fun loadPhoto(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.getPhotoById(id).collect { entity ->
                if (entity != null) {
                    val photo = Photo(
                        id = entity.id,
                        tripName = entity.tripName,
                        imageUrl = entity.imageUrl,
                        timestamp = entity.timestamp,
                        timeHourStamp = entity.timeHourStamp
                    )
                     _uiState.value = _uiState.value.copy(photo = photo, isLoading = false)
                } else {
                     _uiState.value = _uiState.value.copy(isLoading = false)
                }
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
                return GalleryDetailViewModel(repository) as T
            }
        }
    }
}
