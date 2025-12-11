package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication001.model.Museum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ActiveTourUiState(
    val museum: Museum? = null,
    val isLoading: Boolean = false
)

class ActiveTourViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ActiveTourUiState())
    val uiState: StateFlow<ActiveTourUiState> = _uiState.asStateFlow()

    fun loadMuseum(museumId: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val museum = Museum.sampleData.find { it.id == museumId }
            _uiState.value = _uiState.value.copy(
                museum = museum,
                isLoading = false
            )
        }
    }
}
