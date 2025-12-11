package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication001.model.Museum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MuseumListUiState(
    val museums: List<Museum> = emptyList(),
    val isLoading: Boolean = false
)

class MuseumListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MuseumListUiState())
    val uiState: StateFlow<MuseumListUiState> = _uiState.asStateFlow()

    init {
        loadMuseums()
    }

    private fun loadMuseums() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // Simulating data loading
            _uiState.value = _uiState.value.copy(
                museums = Museum.sampleData,
                isLoading = false
            )
        }
    }
}
