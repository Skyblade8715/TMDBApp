package com.skycom.tmdbapp.core.common.data

import android.content.SharedPreferences
import com.skycom.tmdbapp.core.common.domain.LikedStateController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultLikedStateController @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : LikedStateController {

    override fun getMovieLikedState(movieId: Int): Boolean {
        return sharedPreferences.getBoolean("$movieId", false)
    }

    override fun getMoviesLikedState(): List<Int> {
        return sharedPreferences.all.keys.toList().map { it.toInt() }
    }
    override fun setLikedState(movieId: Int, isLiked: Boolean) {
        if (isLiked)
            sharedPreferences.edit().putBoolean("$movieId", true).apply()
        else
            sharedPreferences.edit().remove("$movieId").apply()
    }

}