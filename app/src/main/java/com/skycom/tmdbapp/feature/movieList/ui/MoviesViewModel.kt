package com.skycom.tmdbapp.feature.movieList.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.skycom.tmdbapp.BuildConfig
import com.skycom.tmdbapp.core.common.util.formatter.DefaultDateFormatter
import com.skycom.tmdbapp.core.common.util.formatter.DefaultPathFormatter
import com.skycom.tmdbapp.core.common.util.formatter.DefaultVoteFormatter
import com.skycom.tmdbapp.core.movieList.MovieListRepository
import com.skycom.tmdbapp.core.movieList.model.Movie
import com.skycom.tmdbapp.feature.movieList.model.MoviesUiState
import com.skycom.tmdbapp.feature.movieList.model.UiMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
internal class MoviesViewModel @Inject constructor(
    private val repository: MovieListRepository,
    private val voteFormatter: DefaultVoteFormatter,
    private val dateFormatter: DefaultDateFormatter,
    private val pathFormatter: DefaultPathFormatter,
    @Named("imageBaseUrl") private val imageBaseUrl: String,
    @Named("posterSize") private val posterSize: String,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    val movies: Flow<PagingData<UiMovie>> = uiState.map { it.searchQuery }.flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getNowPlayingMovies()
            } else {
                repository.searchMovies(query)
            }
        }.map { pagingData ->
            pagingData.map { movieToUiMovie(it) }
        }.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            uiState.map { it.searchQuery }.debounce(300).filter { it.isNotEmpty() }
                .flatMapLatest { query ->
                    repository.getAutocompleteSuggestions(query)
                }.collect { suggestions ->
                    val formattedSuggestions = suggestions.map { movieToUiMovie(it) }
                    _uiState.update { it.copy(autocompleteSuggestions = formattedSuggestions) }
                }
        }
    }

    fun toggleLiked(movieId: Int, isLiked: Boolean) {
        viewModelScope.launch {
            repository.setLikedState(movieId, isLiked)
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun clearSearch() {
        _uiState.update { it.copy(searchQuery = "", autocompleteSuggestions = emptyList()) }
    }

    private fun movieToUiMovie(movie: Movie) = UiMovie(
        id = movie.id,
        isLiked = movie.isLiked,
        title = movie.title,
        formattedPosterUrl = pathFormatter.format(movie.posterUrl, imageBaseUrl, posterSize),
        formattedReleaseDate = dateFormatter.format(movie.releaseDate),
        audienceScore = voteFormatter.format(movie.voteAverage, movie.voteCount)
    )
}
