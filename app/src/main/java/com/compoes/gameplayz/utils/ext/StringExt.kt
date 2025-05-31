package com.compoes.gameplayz.utils.ext

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String?.toLocalDate(
    sourceFormat: String? = "yyyy-MM-dd",
    outFormat: String? = "dd MMMM yyyy",
): String {
    if (this.isNullOrBlank()) return this ?: ""
    return try {
        val sourceSdf = SimpleDateFormat(sourceFormat, Locale.US).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }
        val outSdf = SimpleDateFormat(outFormat, Locale.US).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }
        val date = sourceSdf.parse(this) ?: ""
        outSdf.format(date)
    } catch (e: Exception) {
        this
    }
}

fun String?.toEmptyOrString(): String {
    return this ?: ""
}