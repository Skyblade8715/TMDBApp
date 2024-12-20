package com.skycom.tmdbapp.feature.movieList.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.skycom.tmdbapp.core.common.domain.LikedStateController
import com.skycom.tmdbapp.core.common.domain.MovieApiClient
import com.skycom.tmdbapp.core.movieList.MovieListRepository
import com.skycom.tmdbapp.core.movieList.model.Movie
import com.skycom.tmdbapp.core.movieList.model.RawMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton


@Singleton
class DefaultMovieListRepository @Inject constructor(
    private val likedStateController: LikedStateController,
    private val apiClient: MovieApiClient,
    private val movieListPagingSourceFactory: Provider<MovieListPagingSource>,
) : MovieListRepository {

    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { movieListPagingSourceFactory.get() }
        ).flow.map { pagingData ->
            val likedMovies = likedStateController.getMoviesLikedState()
            pagingData.map { rawMovie ->
                rawMovie.toMovie(likedMovies.contains(rawMovie.id))
            }
        }
    }

    override fun setLikedState(movieId: Int, isLiked: Boolean) {
        likedStateController.setLikedState(movieId, isLiked)
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MovieSearchPagingSource(apiClient, query) }
        ).flow.map { pagingData ->
            val likedMovies = likedStateController.getMoviesLikedState()
            pagingData.map { rawMovie ->
                rawMovie.toMovie(likedMovies.contains(rawMovie.id))
            }
        }
    }


    override fun getAutocompleteSuggestions(query: String): Flow<List<Movie>> = flow {
        val results = withContext(Dispatchers.IO) {
            val response = apiClient.getAutocompleteSuggestions(query)
            val likedMovies = likedStateController.getMoviesLikedState()
            response.results.map { rawMovie ->
                rawMovie.toMovie(likedMovies.contains(rawMovie.id))
            }
        }
        emit(results)
    }

    private fun RawMovie.toMovie(isLiked: Boolean): Movie {
        return Movie(
            id = this.id,
            isLiked = isLiked,
            title = this.title,
            overview = this.overview,
            popularity = this.popularity,
            posterUrl = this.posterPath ?: "",
            releaseDate = this.releaseDate,
            voteAverage = this.voteAverage,
            voteCount = this.voteCount,
        )
    }
}
