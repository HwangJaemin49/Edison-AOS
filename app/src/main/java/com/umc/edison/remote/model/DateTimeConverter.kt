package com.umc.edison.remote.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toIso8601String(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return dateFormat.format(this)
}

fun String.toDate(): Date? {
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        dateFormat.parse(this)
    } catch (e: Exception) {
        null
    }
}