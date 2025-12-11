package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.myapplication001.model.Museum

data class MuseumDetailUiState(
    val isLoading: Boolean = false,
    val museum: Museum? = null
)

class MuseumDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MuseumDetailUiState())
    val uiState: StateFlow<MuseumDetailUiState> = _uiState.asStateFlow()
}
