package com.laioffer.spotify.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laioffer.spotify.datamodel.Section
import com.laioffer.spotify.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(feed = emptyList(), isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun fetchHomeScreen() {
        viewModelScope.launch {
            val sections = repository.getHomeSections()
            _uiState.value = HomeUiState(feed = sections, isLoading = false)
            Log.d("HomeViewModel", _uiState.value.toString())
        }
    }
}

data class HomeUiState(val feed: List<Section>, val isLoading: Boolean)


