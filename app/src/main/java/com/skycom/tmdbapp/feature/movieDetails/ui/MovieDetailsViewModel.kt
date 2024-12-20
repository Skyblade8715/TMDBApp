package com.skycom.tmdbapp.feature.movieDetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skycom.tmdbapp.core.common.model.UiState
import com.skycom.tmdbapp.core.common.util.formatter.DefaultDateFormatter
import com.skycom.tmdbapp.core.common.util.formatter.DefaultPathFormatter
import com.skycom.tmdbapp.core.common.util.formatter.DefaultVoteFormatter
import com.skycom.tmdbapp.core.movieDetails.MovieDetailsRepository
import com.skycom.tmdbapp.core.movieDetails.model.MovieDetails
import com.skycom.tmdbapp.feature.movieDetails.model.UiMovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
internal class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieDetailsRepository,
    private val voteFormatter: DefaultVoteFormatter,
    private val dateFormatter: DefaultDateFormatter,
    private val pathFormatter: DefaultPathFormatter,
    @Named("imageBaseUrl") private val imageBaseUrl: String,
    @Named("backdropSize") private val backdropSize: String,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<UiMovieDetails>>(UiState.Loading)
    val uiState: StateFlow<UiState<UiMovieDetails>> = _uiState

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val details = repository.getMovieDetails(movieId)
                _uiState.value = UiState.Success(details.toUiMovieDetails())
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }

    private fun MovieDetails.toUiMovieDetails() = UiMovieDetails(
        id = this.id,
        isLiked = this.isLiked,
        title = this.title,
        overview = this.overview,
        formattedBackdropUrl = pathFormatter.format(
            this.formattedBackdropPath,
            imageBaseUrl,
            backdropSize,
        ),
        formattedReleaseDate = dateFormatter.format(this.releaseDate),
        audienceScore = voteFormatter.format(this.voteAverage, this.voteCount),
    )

    fun toggleLiked() {
        val currentState = _uiState.value
        if (currentState is UiState.Success) {
            val currentDetails = currentState.data
            val updatedDetails = currentDetails.copy(isLiked = !currentDetails.isLiked)
            _uiState.value = UiState.Success(updatedDetails)
            viewModelScope.launch {
                repository.setLikedState(updatedDetails.id, updatedDetails.isLiked)
            }
        }
    }

}

