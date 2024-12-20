package com.skycom.tmdbapp.core.movieDetails

import com.skycom.tmdbapp.core.movieDetails.model.MovieDetails

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId: Int) : MovieDetails
    fun setLikedState(movieId: Int, isLiked: Boolean)
}