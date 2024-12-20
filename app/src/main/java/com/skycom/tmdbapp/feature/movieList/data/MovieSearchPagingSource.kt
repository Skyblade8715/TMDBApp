package com.skycom.tmdbapp.feature.movieList.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skycom.tmdbapp.core.common.domain.MovieApiClient
import com.skycom.tmdbapp.core.movieList.model.RawMovie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieSearchPagingSource @Inject constructor(
    private val apiClient: MovieApiClient,
    private val query: String
) : PagingSource<Int, RawMovie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RawMovie> {
        return try {
            val page = params.key ?: 1
            val searchResponse = apiClient.searchMovies(query, page)

            LoadResult.Page(
                data = searchResponse.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (searchResponse.results.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RawMovie>): Int? = null
}
