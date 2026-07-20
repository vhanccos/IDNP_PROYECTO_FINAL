package com.example.myapplication001.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication001.MyApplication
import com.example.myapplication001.data.repository.MuseumRepository
import com.example.myapplication001.data.repository.SyncResult
import com.example.myapplication001.model.Museum
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class MuseumListUiState(
    val isLoading: Boolean = false,
    val museums: List<Museum> = emptyList(),
    val isOffline: Boolean = false,             // ← nuevo
    val syncResult: SyncResult = SyncResult.Idle // ← nuevo
)

class MuseumListViewModel(private val repository: MuseumRepository) : ViewModel() {

    val uiState: StateFlow<MuseumListUiState> = combine(
        repository.museums,
        repository.networkMonitor.isOnline,
        repository.syncResult
    ) { entities, isOnline, syncResult ->
        // Mismo mapeo entity→Museum que tenías antes
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
        MuseumListUiState(
            museums = museums,
            isLoading = syncResult is SyncResult.Syncing && museums.isEmpty(),
            isOffline = !isOnline,
            syncResult = syncResult
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MuseumListUiState(isLoading = true)
    )

    init {
        sync()
    }

    // Llamado desde el botón de reintento en la UI
    fun sync() {
        viewModelScope.launch {
            repository.syncAll()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(
                    extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                )
                val repository = (application as MyApplication).museumRepository
                return MuseumListViewModel(repository) as T
            }
        }
    }
}