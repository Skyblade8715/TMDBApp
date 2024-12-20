package com.skycom.tmdbapp.core.movieList

import androidx.paging.PagingData
import com.skycom.tmdbapp.core.movieList.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    fun getNowPlayingMovies(): Flow<PagingData<Movie>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
    fun getAutocompleteSuggestions(query: String): Flow<List<Movie>>
    fun setLikedState(movieId: Int, isLiked: Boolean)
}