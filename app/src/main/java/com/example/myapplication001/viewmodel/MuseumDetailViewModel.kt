package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.myapplication001.model.Museum

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.myapplication001.data.repository.MuseumRepository
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication001.MyApplication

data class MuseumDetailUiState(
    val isLoading: Boolean = false,
    val museum: Museum? = null
)

class MuseumDetailViewModel(private val repository: MuseumRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(MuseumDetailUiState())
    val uiState: StateFlow<MuseumDetailUiState> = _uiState.asStateFlow()

    fun loadMuseum(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // Ideally we observe a Flow from Dao
            repository.getMuseumById(id).collect { entity ->
                if (entity != null) {
                    val museum = Museum(
                         id = entity.id,
                         name = entity.name,
                         description = entity.description,
                         imageUrl = entity.imageUrl,
                         ratingValue = entity.ratingValue,
                         ratingCount = entity.ratingCount,
                         infoText = entity.infoText,
                         funFactTitle = entity.funFactTitle,
                         funFactText = entity.funFactText
                    )
                    _uiState.value = _uiState.value.copy(museum = museum, isLoading = false)
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
                val repository = (application as MyApplication).museumRepository
                return MuseumDetailViewModel(repository) as T
            }
        }
    }
}
