package com.skycom.tmdbapp.core.movieDetails.model

data class MovieDetails(
    val formattedBackdropPath: String,
    val genres: List<Genre>,
    val id: Int,
    val isLiked: Boolean,
    val originalLanguage: String,
    val originalTitle: String,
    val releaseDate: String,
    val overview: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
)