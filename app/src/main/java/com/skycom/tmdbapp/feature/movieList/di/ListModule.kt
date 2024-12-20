package com.skycom.tmdbapp.feature.movieList.di

import com.skycom.tmdbapp.core.movieList.MovieListRepository
import com.skycom.tmdbapp.feature.movieList.data.DefaultMovieListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ListModule {

    @Provides
    @Singleton
    fun provideMovieListRepository(
        default: DefaultMovieListRepository
    ): MovieListRepository = default
}