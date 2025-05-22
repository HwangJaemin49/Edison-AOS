package com.umc.edison.remote.token

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private var ACCESS_TOKEN: String? = null
        private var REFRESH_TOKEN: String? = null
    }

    fun loadAccessToken(): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        ACCESS_TOKEN = sharedPreferences.getString("access_token", null)

        return ACCESS_TOKEN
    }

    fun loadRefreshToken(): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        REFRESH_TOKEN = sharedPreferences.getString("refresh_token", null)

        return REFRESH_TOKEN
    }

    fun setToken(accessToken: String, refreshToken: String? = null) {
        ACCESS_TOKEN = accessToken
        REFRESH_TOKEN = refreshToken

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putString("access_token", ACCESS_TOKEN)
            if (REFRESH_TOKEN != null) putString("refresh_token", REFRESH_TOKEN)
        }
    }

    fun deleteToken() {
        ACCESS_TOKEN = null
        REFRESH_TOKEN = null

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        sharedPreferences.edit {
            remove("access_token")
            remove("refresh_token")
        }
    }
}