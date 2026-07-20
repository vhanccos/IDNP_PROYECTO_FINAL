    package com.example.myapplication001.viewmodel

    import androidx.lifecycle.ViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import com.example.myapplication001.model.Trip
    import androidx.lifecycle.viewModelScope
    import kotlinx.coroutines.launch
    import com.example.myapplication001.data.repository.UserDataRepository
    import androidx.lifecycle.ViewModelProvider
    import androidx.lifecycle.viewmodel.CreationExtras
    import com.example.myapplication001.MyApplication

    data class TripListUiState(
        val isLoading: Boolean = false,
        val trips: List<Trip> = emptyList()
    )

    class TripListViewModel(private val repository: UserDataRepository) : ViewModel() {
        private val _uiState = MutableStateFlow(TripListUiState())
        val uiState: StateFlow<TripListUiState> = _uiState.asStateFlow()

        init {
            loadTrips()
        }

        private fun loadTrips() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true)

                // Intentar refrescar datos si la lista está vacía
                try {
                    repository.refreshTrips()
                    repository.refreshPhotos()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                repository.trips.collect { entities ->
                    val trips = entities.map { entity ->
                        Trip(
                            name = entity.name,
                            description = entity.description,
                            imageUrl = entity.imageUrl
                        )
                    }
                    _uiState.value = _uiState.value.copy(
                        trips = trips,
                        isLoading = false
                    )
                }
            }
        }

        fun createTrip(name: String, description: String, imageUrl: String) {
            viewModelScope.launch {
                repository.createTrip(name, description, imageUrl)
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
                    val repository = (application as MyApplication).userDataRepository
                    return TripListViewModel(repository) as T
                }
            }
        }
    }