package com.laioffer.spotify.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laioffer.spotify.datamodel.Album
import com.laioffer.spotify.repository.FavoriteAlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteAlbumRepository: FavoriteAlbumRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState(emptyList()))
    val uiState: StateFlow<FavoriteUiState> = _uiState

    init {
        viewModelScope.launch {
            favoriteAlbumRepository.fetchFavoriteAlbums().collect {
                _uiState.value = FavoriteUiState(it)
            }
        }
    }

}

data class FavoriteUiState(
    val albums: List<Album>
)

