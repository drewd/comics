package com.dobson.comics.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dobson.comics.model.Comic
import com.dobson.comics.repository.ComicRepository
import com.dobson.comics.repository.successOr
import com.dobson.comics.ui.MainDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailsUiState(
    val comic: Comic? = null,
    val previousComicId: Int? = null,
    val nextComicId: Int? = null,
    val loading: Boolean = false,
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ComicRepository,
) : ViewModel() {

    private val comicId = checkNotNull<Int>(savedStateHandle[MainDestinations.COMIC_ID_KEY])
    private val _uiState = MutableStateFlow(DetailsUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    private fun refresh() {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            val comicDeferred = async { repository.get(comicId) }
            val comic = comicDeferred.await().successOr(null)
            _uiState.update {
                it.copy(
                    loading = false,
                    comic = comic,
                    previousComicId = comic?.id?.dec()?.run { if (this > 1) this else null },
                    nextComicId = comic?.id?.inc()?.run { if (this > 20) null else this },
                )
            }
        }
    }
}