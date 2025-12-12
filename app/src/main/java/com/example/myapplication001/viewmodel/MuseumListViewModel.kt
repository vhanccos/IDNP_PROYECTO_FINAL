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

data class MuseumListUiState(
    val isLoading: Boolean = false,
    val museums: List<Museum> = emptyList()
)

class MuseumListViewModel(private val repository: MuseumRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(MuseumListUiState())
    val uiState: StateFlow<MuseumListUiState> = _uiState.asStateFlow()

    init {
        loadMuseums()
    }

    private fun loadMuseums() {
        viewModelScope.launch {
             _uiState.value = _uiState.value.copy(isLoading = true)
             // Ensure data is fresh (or just rely on what HomeViewModel might have loaded? Better to refresh/ensure)
             repository.refreshMuseums()
             
             repository.museums.collect { entities ->
                 val museums = entities.map { entity ->
                    Museum(
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
                 }
                 _uiState.value = _uiState.value.copy(
                     museums = museums,
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
                val repository = (application as MyApplication).museumRepository
                return MuseumListViewModel(repository) as T
            }
        }
    }
}
