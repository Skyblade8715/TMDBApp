package com.skycom.tmdbapp.feature.movieDetails.model

data class UiMovieDetails(
    val id: Int,
    val isLiked: Boolean,
    val title: String,
    val overview: String,
    val formattedBackdropUrl: String,
    val formattedReleaseDate: String,
    val audienceScore: String
)
