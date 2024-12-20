package com.skycom.tmdbapp.feature.movieList.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skycom.tmdbapp.core.common.domain.MovieApiClient
import com.skycom.tmdbapp.core.movieList.model.RawMovie
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieListPagingSource @Inject constructor(
    private val apiClient: MovieApiClient
) : PagingSource<Int, RawMovie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RawMovie> {
        return try {
            val page = params.key ?: 1
            val movieResponse = apiClient.getNowPlayingMovies(page)

            LoadResult.Page(
                data = movieResponse.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < movieResponse.totalPages) page + 1 else null,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RawMovie>): Int? = null
}