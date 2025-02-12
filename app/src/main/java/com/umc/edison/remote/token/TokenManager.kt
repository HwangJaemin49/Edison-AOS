package com.umc.edison.remote.token

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private var ACCESS_TOKEN: String? = null
        private var REFRESH_TOKEN: String? = null
    }

    fun loadAccessToken(): String? {
        setToken(
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMyIsImV4cCI6MTc0MTk5NTEyOSwiZW1haWwiOiJlc3RlbGxlMDMyOUBld2hhLmFjLmtyIn0.71zIx_yyWPhLFuZh3zs9lmHt1ecB_K7fPx8LSYAN4FE",
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMyIsImV4cCI6MTc1NTA5MDE4NSwiZW1haWwiOiJlc3RlbGxlMDMyOUBld2hhLmFjLmtyIn0.M9TRlbloK65uP4BGJf4hdXjKQZWy-IcfhdEqJutYzaQ"
        )
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
        val editor = sharedPreferences.edit()
        editor.putString("access_token", ACCESS_TOKEN)
        if (REFRESH_TOKEN != null) editor.putString("refresh_token", REFRESH_TOKEN)
        editor.apply()
    }

    fun deleteToken() {
        ACCESS_TOKEN = null
        REFRESH_TOKEN = null

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("access_token")
        editor.remove("refresh_token")
        editor.apply()
    }
}