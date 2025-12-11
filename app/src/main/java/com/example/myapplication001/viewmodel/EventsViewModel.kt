package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication001.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EventsUiState(
    val events: List<Event> = emptyList(),
    val showNotificationCard: Boolean = true,
    val isLoading: Boolean = false
)

class EventsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EventsUiState())
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // Simulating data loading
            _uiState.value = _uiState.value.copy(
                events = Event.sampleData,
                isLoading = false
            )
        }
    }

    fun dismissNotification() {
        _uiState.value = _uiState.value.copy(showNotificationCard = false)
    }
}
