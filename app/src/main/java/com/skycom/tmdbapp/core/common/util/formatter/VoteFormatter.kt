package com.skycom.tmdbapp.core.common.util.formatter


fun interface VoteFormatter {
    fun format(voteAverage: Double, voteCount: Int): String
}

class DefaultVoteFormatter : VoteFormatter {
    override fun format(voteAverage: Double, voteCount: Int): String {
        try {
            val stars =
                "★".repeat((voteAverage / 2).toInt()) + "☆".repeat(5 - (voteAverage / 2).toInt())
            val percentage = (voteAverage * 10).toInt()
            return "$stars $percentage% ($voteCount)"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}