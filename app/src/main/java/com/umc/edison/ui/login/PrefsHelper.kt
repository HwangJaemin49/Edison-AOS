package com.umc.edison.ui.login

import android.content.Context
import android.content.SharedPreferences

object PrefsHelper {

    private const val PREFS_NAME = "app_prefs"
    private const val KEY_MAIN_SCREEN_VISITED = "main_screen_visited"

    fun hasVisitedMainScreen(context: Context): Boolean {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_MAIN_SCREEN_VISITED, false)
    }

    fun setMainScreenVisited(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_MAIN_SCREEN_VISITED, true).apply()
    }
}