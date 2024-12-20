package com.skycom.tmdbapp.feature.movieDetails.di

import com.skycom.tmdbapp.core.movieDetails.MovieDetailsDataSource
import com.skycom.tmdbapp.core.movieDetails.MovieDetailsRepository
import com.skycom.tmdbapp.feature.movieDetails.data.DefaultMovieDetailsDataSource
import com.skycom.tmdbapp.feature.movieDetails.data.DefaultMovieDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DetailsModule {

    @Provides
    @Singleton
    fun provideMovieDetailsDataSource(
        default: DefaultMovieDetailsDataSource
    ): MovieDetailsDataSource = default

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(
        default: DefaultMovieDetailsRepository
    ): MovieDetailsRepository = default
}