package com.umc.edison.remote.token

import android.util.Log
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

        if (token.isNullOrEmpty()) {
            val errorResponse = BaseResponse(
                isSuccess = false,
                code = "LOGIN4002",
                message = "로그인이 필요합니다."
            )

            val jsonResponse = Gson().toJson(errorResponse)
            val responseBody = jsonResponse.toResponseBody("application/json".toMediaTypeOrNull())

            return Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(401)
                .message("Unauthorized")
                .body(responseBody)
                .build()
        }

        val request = chain.request().newBuilder()
            .addHeader(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
            .build()

        return chain.proceed(request)
    }
}
