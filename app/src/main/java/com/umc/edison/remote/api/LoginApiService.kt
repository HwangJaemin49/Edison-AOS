package com.umc.edison.remote.api

import com.umc.edison.remote.model.BaseResponse
import com.umc.edison.remote.model.ResponseWithData
import com.umc.edison.remote.model.login.IdTokenRequest
import com.umc.edison.remote.model.login.NicknameRequest
import com.umc.edison.remote.model.login.SetIdentityKeywordRequest
import com.umc.edison.remote.model.login.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {
    @POST("members/google")
    suspend fun googleLogin( @Body request: IdTokenRequest): ResponseWithData<TokenResponse>
    @POST("members/register")
    suspend fun makeNickName(
        @Body request: NicknameRequest
    ): BaseResponse
    @POST("/members/identity")
    suspend fun setUserIdentityAndInterest(
        @Body request: SetIdentityKeywordRequest
    ): BaseResponse
}