package com.umc.edison.remote.token

import com.google.gson.Gson
import com.umc.edison.remote.model.BaseResponse
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
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
