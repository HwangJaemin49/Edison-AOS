package com.umc.edison.data.token

interface AccessTokenProvider {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun clearCachedTokens()
    fun setCachedTokens(accessToken: String, refreshToken: String?)
}