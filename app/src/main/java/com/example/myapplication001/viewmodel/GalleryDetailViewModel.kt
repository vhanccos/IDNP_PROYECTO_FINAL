package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GalleryDetailUiState(
    val isLoading: Boolean = false
)

class GalleryDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GalleryDetailUiState())
    val uiState: StateFlow<GalleryDetailUiState> = _uiState.asStateFlow()
}
