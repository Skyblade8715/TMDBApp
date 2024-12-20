package com.skycom.tmdbapp.core.common.domain

import com.skycom.tmdbapp.core.movieDetails.model.RawMovieDetails
import com.skycom.tmdbapp.core.movieList.model.RawMovieResponse
import com.skycom.tmdbapp.core.movieList.model.SearchResponse

interface MovieApiClient {
    suspend fun getNowPlayingMovies(page: Int): RawMovieResponse
    suspend fun searchMovies(query: String, page: Int): SearchResponse
    suspend fun getAutocompleteSuggestions(query: String): SearchResponse
    suspend fun getMovieDetails(movieId: Int): RawMovieDetails
}