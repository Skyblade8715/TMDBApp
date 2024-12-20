package com.skycom.tmdbapp.feature.movieList.model

data class UiMovie(
    val id: Int,
    val title: String,
    val isLiked: Boolean,
    val formattedPosterUrl: String,
    val formattedReleaseDate: String,
    val audienceScore: String
)
