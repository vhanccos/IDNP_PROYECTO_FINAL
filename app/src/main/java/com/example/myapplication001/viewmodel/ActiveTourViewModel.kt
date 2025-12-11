package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ActiveTourUiState(
    val isLoading: Boolean = false
)

class ActiveTourViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ActiveTourUiState())
    val uiState: StateFlow<ActiveTourUiState> = _uiState.asStateFlow()
}
