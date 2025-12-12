package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.myapplication001.model.Event
import com.example.myapplication001.data.repository.MuseumRepository
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication001.MyApplication

data class EventsUiState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList()
)

class EventsViewModel(private val repository: MuseumRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(EventsUiState())
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.refreshEvents()
            
            repository.events.collect { entities ->
                val events = entities.map { entity ->
                    Event(
                        name = entity.name,
                        date = entity.date,
                        time = entity.time,
                        location = entity.location,
                        type = entity.type
                    )
                }
                _uiState.value = _uiState.value.copy(
                    events = events,
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
                return EventsViewModel(repository) as T
            }
        }
    }
}
