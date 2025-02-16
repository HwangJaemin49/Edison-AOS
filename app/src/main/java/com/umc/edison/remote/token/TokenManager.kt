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
//        setToken(
//            accessToken ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiZXhwIjoxNzQyMDQwMTk4LCJlbWFpbCI6InJoa3J0anNnaDEyMTBAZ21haWwuY29tIn0.xc02mDqXxfJVNFhWk_3_FE9zXi5ax738GKIPCftcvpc",
//            refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiZXhwIjoxNzU1MTM1MjU0LCJlbWFpbCI6InJoa3J0anNnaDEyMTBAZ21haWwuY29tIn0.qyARgaPhwd_ajKxSgwoHZ4Y6VCY90tnM9Yj_y—naxQ"
//        )
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