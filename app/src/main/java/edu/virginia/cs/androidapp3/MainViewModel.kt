package edu.virginia.cs.androidapp3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainUIState(
    val loading: Boolean,
    val activeTagName: String,
    val error: Boolean
)

// Google Gemini 3 Pro helped me write, debug, and design this complex view model that pulls from multiple flows
class MainViewModel(
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MainUIState(
            loading = false,
            activeTagName = "core",
            error = false
        )
    )

    val uiState = _uiState.asStateFlow()

    // Needs a refresh when it starts
    init {
        refresh()
    }

    // Got the idea to use 'combine' from Gemini 3 Pro
    // This filters for all the locations that match the current tag
    val locations = combine(locationRepository.getLocationsWithTags(), uiState) { locations, uiState ->
        locations.filter { locationWithTags ->
            locationWithTags.tags.any { tag -> tag.name == uiState.activeTagName }
        }
    }.stateIn(
        scope = viewModelScope,  // make the 'cold' flow 'hot' in the viewModelScope
        started = SharingStarted.WhileSubscribed(5000),  // only stop sharing if more than 5s pass
        initialValue = listOf()  // value to display before flow is first emitted
    )

    val uniqueTagList = locationRepository.getUniqueTags()
        .stateIn(
            scope = viewModelScope,  // make the 'cold' flow 'hot' in the viewModelScope
            started = SharingStarted.WhileSubscribed(5000),  // only stop sharing if more than 5s pass
            initialValue = listOf() // value to display before flow is first emitted
        )

    fun updateTag(tagName: String) {
        _uiState.update { uiState ->
            uiState.copy(activeTagName = tagName)
        }
    }

    fun refresh() {
        _uiState.update { uiState ->
            uiState.copy(loading = true, error = false)
        }

        viewModelScope.launch {
            val result = locationRepository.refreshLocationsWithTags()
            // Gemini 3 suggested refactoring updated to uiState into one block (a good suggestion, so I took it)
            _uiState.update { uiState ->
                uiState.copy(
                    loading = false,
                    error = result == RefreshResult.ERROR
                )
            }
        }
    }
}