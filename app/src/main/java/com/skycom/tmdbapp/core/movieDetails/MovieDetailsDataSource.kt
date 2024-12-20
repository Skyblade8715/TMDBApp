package com.skycom.tmdbapp.core.movieDetails

import com.skycom.tmdbapp.core.movieDetails.model.RawMovieDetails

interface MovieDetailsDataSource {
    suspend fun getMovieDetails(movieId: Int) : RawMovieDetails
}