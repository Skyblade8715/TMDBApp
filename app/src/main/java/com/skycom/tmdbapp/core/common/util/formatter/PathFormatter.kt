package com.skycom.tmdbapp.core.common.util.formatter

interface PathFormatter {
    fun format(posterPath: String, basePath: String, size: String): String
}

class DefaultPathFormatter : PathFormatter {
    override fun format(posterPath: String, basePath: String, size: String): String {
        try {
            return "${basePath}${size}$posterPath"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}