package com.umc.edison.remote.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toIso8601String(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return dateFormat.format(this)
}

fun parseIso8601ToDate(isoDateTime: String): Date {
    return try {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        simpleDateFormat.parse(isoDateTime)!!
    } catch (e: Exception) {
        e.printStackTrace()
        Date()
    }
}