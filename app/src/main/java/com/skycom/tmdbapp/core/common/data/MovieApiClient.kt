package com.skycom.tmdbapp.core.common.data

import com.google.gson.Gson
import com.skycom.tmdbapp.BuildConfig
import com.skycom.tmdbapp.core.common.domain.MovieApiClient
import com.skycom.tmdbapp.core.movieDetails.model.RawMovieDetails
import com.skycom.tmdbapp.core.movieList.model.RawMovieResponse
import com.skycom.tmdbapp.core.movieList.model.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

// TODO - use Retrofit
@Singleton
class DefaultMovieApiClient @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson
) : MovieApiClient {
    private val baseUrl = BuildConfig.MOVIE_DB_BASE_URL
    private val apiToken = BuildConfig.MOVIE_DB_API_TOKEN

    override suspend fun getNowPlayingMovies(page: Int): RawMovieResponse {
        val url = "${baseUrl}movie/now_playing?language=en-US&page=$page"
        val request = Request.Builder()
            .url(url)
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $apiToken")
            .build()

        return executeRequest(request, RawMovieResponse::class.java)
    }

    override suspend fun searchMovies(query: String, page: Int): SearchResponse {
        val url = "${baseUrl}search/movie?query=$query&language=en-US&page=$page"
        val request = Request.Builder()
            .url(url)
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $apiToken")
            .build()

        return executeRequest(request, SearchResponse::class.java)
    }

    override suspend fun getAutocompleteSuggestions(query: String): SearchResponse {
        val url = "${baseUrl}search/movie?query=$query&include_adult=false&language=en-US&page=1"
        val request = Request.Builder()
            .url(url)
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $apiToken")
            .build()

        return executeRequest(request, SearchResponse::class.java)
    }

    override suspend fun getMovieDetails(movieId: Int): RawMovieDetails {
        val url = "${baseUrl}movie/$movieId?language=en-US"

        val request = Request.Builder()
            .url(url)
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $apiToken")
            .build()

        return executeRequest(request, RawMovieDetails::class.java)
    }

    private suspend fun <T> executeRequest(request: Request, clazz: Class<T>): T {
        return withContext(Dispatchers.IO) {
            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw Exception("Network error: ${response.code} - ${response.message}")
                }
                val bodyString = response.body?.string() ?: throw Exception("Empty response")
                gson.fromJson(bodyString, clazz)
            }
        }
    }
}
