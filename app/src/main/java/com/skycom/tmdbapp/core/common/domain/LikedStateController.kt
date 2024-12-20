package com.skycom.tmdbapp.core.common.domain

interface LikedStateController {
    fun getMovieLikedState(movieId: Int): Boolean
    fun getMoviesLikedState(): List<Int>
    fun setLikedState(movieId: Int, isLiked: Boolean)
}