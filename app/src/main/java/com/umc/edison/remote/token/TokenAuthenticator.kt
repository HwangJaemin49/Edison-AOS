package com.umc.edison.remote.token

import com.google.gson.Gson
import com.umc.edison.remote.api.RefreshTokenApiService
import com.umc.edison.remote.model.BaseResponse
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val refreshTokenApiService: RefreshTokenApiService
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 3) return null

        val errorBody = response.peekBody(Long.MAX_VALUE).string()
        val errorResponse = Gson().fromJson(errorBody, BaseResponse::class.java)

        if (errorResponse?.code != "LOGIN4004") return null

        val currentToken = tokenManager.loadAccessToken()
        val requestToken = response.request.header("Authorization")?.substringAfter("Bearer ") ?: ""

        if (currentToken != requestToken) {
            return response.request.newBuilder()
                .header("Authorization", "Bearer $currentToken")
                .build()
        }

        synchronized(tokenManager) {
            val newToken = tokenManager.loadAccessToken()
            if (requestToken != newToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
            }

            val refreshToken = tokenManager.loadRefreshToken()
            if (refreshToken.isNullOrEmpty()) {
                tokenManager.deleteToken()
                return null
            }

            val newTokenResponse = refreshTokenApiService.refreshToken(refreshToken, "Bearer $currentToken").execute()
            val refreshedToken = newTokenResponse.body()?.data?.accessToken ?: return null

            tokenManager.setToken(refreshedToken)

            return response.request.newBuilder()
                .header("Authorization", "Bearer $refreshedToken")
                .build()
        }
    }

    private fun responseCount(response: Response): Int {
        var result = 1
        var priorResponse = response.priorResponse
        while (priorResponse != null) {
            result++
            priorResponse = priorResponse.priorResponse
        }
        return result
    }
}