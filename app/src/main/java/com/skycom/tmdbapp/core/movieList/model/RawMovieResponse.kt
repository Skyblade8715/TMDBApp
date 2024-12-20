package com.skycom.tmdbapp.core.movieList.model

import com.google.gson.annotations.SerializedName

data class RawMovieResponse(
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
    val results: List<RawMovie>,
    val dates: DatesRange,
)
