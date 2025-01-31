package com.umc.edison.remote.token

import com.google.gson.Gson
import com.umc.edison.remote.api.RefreshTokenApiService
import com.umc.edison.remote.model.BaseResponse
import kotlinx.coroutines.runBlocking
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
        val errorBody = response.body?.string()
        val errorResponse = Gson().fromJson(errorBody, BaseResponse::class.java)

        if (errorResponse?.code == "LOGIN4005") {
            val newToken: String = runBlocking {
                val refreshToken = tokenManager.loadRefreshToken() ?: return@runBlocking ""
                refreshTokenApiService.refreshToken("Bearer $refreshToken").data.accessToken
            }

            if (newToken.isEmpty()) {
                return null
            }

            tokenManager.setToken(newToken)

            return response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        }
        return null
    }
}