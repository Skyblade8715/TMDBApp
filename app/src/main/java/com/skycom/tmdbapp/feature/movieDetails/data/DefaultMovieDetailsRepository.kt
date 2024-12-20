package com.skycom.tmdbapp.feature.movieDetails.data

import com.skycom.tmdbapp.core.common.domain.LikedStateController
import com.skycom.tmdbapp.core.movieDetails.MovieDetailsDataSource
import com.skycom.tmdbapp.core.movieDetails.MovieDetailsRepository
import com.skycom.tmdbapp.core.movieDetails.model.MovieDetails
import com.skycom.tmdbapp.core.movieDetails.model.RawMovieDetails
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DefaultMovieDetailsRepository @Inject constructor(
    private val movieDetailsDataSource: MovieDetailsDataSource,
    private val likedStateController: LikedStateController
) : MovieDetailsRepository {

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return movieDetailsDataSource.getMovieDetails(movieId).toMovieDetails()
    }

    override fun setLikedState(movieId: Int, isLiked: Boolean) {
        likedStateController.setLikedState(movieId, isLiked)
    }

    private fun RawMovieDetails.toMovieDetails() = MovieDetails(
        formattedBackdropPath = this.backdropPath,
        genres = this.genres,
        id = this.id,
        isLiked = likedStateController.getMovieLikedState(this.id),
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        releaseDate = this.releaseDate,
        overview = this.overview,
        title = this.title,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}
