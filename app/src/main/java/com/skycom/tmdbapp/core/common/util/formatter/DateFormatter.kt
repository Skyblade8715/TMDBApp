package com.skycom.tmdbapp.core.common.util.formatter

import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun interface DateFormatter {
    fun format(date: String): String
}

class DefaultDateFormatter : DateFormatter {
    override fun format(date: String): String {
        try {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

            val formattedDate = LocalDate.parse(date, inputFormatter)
            return formattedDate.format(outputFormatter)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}