package com.skycom.tmdbapp.feature.movieList.model

data class MoviesUiState(
    val searchQuery: String = "",
    val autocompleteSuggestions: List<UiMovie> = emptyList()
)
