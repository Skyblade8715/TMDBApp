package com.skycom.tmdbapp.core.common.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.skycom.tmdbapp.BuildConfig
import com.skycom.tmdbapp.core.common.data.DefaultLikedStateController
import com.skycom.tmdbapp.core.common.data.DefaultMovieApiClient
import com.skycom.tmdbapp.core.common.domain.LikedStateController
import com.skycom.tmdbapp.core.common.domain.MovieApiClient
import com.skycom.tmdbapp.core.common.util.formatter.DefaultDateFormatter
import com.skycom.tmdbapp.core.common.util.formatter.DefaultPathFormatter
import com.skycom.tmdbapp.core.common.util.formatter.DefaultVoteFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    @Singleton
    fun provideDefaultDateFormatter(): DefaultDateFormatter = DefaultDateFormatter()

    @Provides
    @Singleton
    fun provideDefaultVoteFormatter(): DefaultVoteFormatter = DefaultVoteFormatter()

    @Provides
    @Singleton
    fun provideDefaultPathFormatter(): DefaultPathFormatter = DefaultPathFormatter()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    @Named("imageBaseUrl")
    fun provideImageBaseUrl(): String = BuildConfig.MOVIE_IMAGE_BASE_URL

    @Provides
    @Singleton
    @Named("posterSize")
    fun providePosterSize(): String = BuildConfig.MOVIE_POSTER_SIZE

    @Provides
    @Singleton
    @Named("backdropSize")
    fun provideBackdropSize(): String = BuildConfig.MOVIE_BACKDROP_SIZE

    @Provides
    fun providePreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName, MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideLikedStateController(default: DefaultLikedStateController): LikedStateController =
        default

    @Provides
    @Singleton
    fun provideMovieApiClient(default: DefaultMovieApiClient): MovieApiClient = default
}