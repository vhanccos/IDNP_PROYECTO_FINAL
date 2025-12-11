package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.myapplication001.model.Museum

data class MuseumListUiState(
    val isLoading: Boolean = false,
    val museums: List<Museum> = emptyList()
)

class MuseumListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MuseumListUiState())
    val uiState: StateFlow<MuseumListUiState> = _uiState.asStateFlow()
}
