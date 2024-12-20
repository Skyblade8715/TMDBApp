package com.skycom.tmdbapp.core.movieList.model

data class Movie(
    val id: Int,
    val isLiked: Boolean,
    val title: String,
    val overview: String,
    val popularity: Double,
    val posterUrl: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
)
