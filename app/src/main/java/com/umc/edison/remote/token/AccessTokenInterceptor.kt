package com.umc.edison.remote.token

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TOKEN_TYPE = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.loadAccessToken()

        if (token == null) {
            return chain.proceed(chain.request())
        } else {
            val request = chain.request().newBuilder()
                .addHeader(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
                .build()

            return chain.proceed(request)
        }
    }
}
