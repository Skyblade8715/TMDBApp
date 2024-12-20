package com.skycom.tmdbapp.feature.movieDetails.data

import com.skycom.tmdbapp.core.common.domain.MovieApiClient
import com.skycom.tmdbapp.core.movieDetails.MovieDetailsDataSource
import com.skycom.tmdbapp.core.movieDetails.model.RawMovieDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultMovieDetailsDataSource @Inject constructor(
    private val movieApiClient: MovieApiClient
) : MovieDetailsDataSource {

    override suspend fun getMovieDetails(movieId: Int): RawMovieDetails {
        return movieApiClient.getMovieDetails(movieId)
    }
}