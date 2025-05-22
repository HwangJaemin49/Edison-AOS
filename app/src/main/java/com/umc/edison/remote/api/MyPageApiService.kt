package com.umc.edison.remote.api

import com.umc.edison.remote.model.BaseResponse
import com.umc.edison.remote.model.ResponseWithData
import com.umc.edison.remote.model.ResponseWithPagination
import com.umc.edison.remote.model.mypage.GetIdentityKeywordResponse
import com.umc.edison.remote.model.mypage.GetAllMyTestResultsResponse
import com.umc.edison.remote.model.mypage.GetMyScrapArtLettersResponse
import com.umc.edison.remote.model.mypage.GetProfileInfoResponse
import com.umc.edison.remote.model.mypage.GetScrapArtLettersByCategoryResponse
import com.umc.edison.remote.model.mypage.UpdateTestRequest
import com.umc.edison.remote.model.mypage.UpdateTestResponse
import com.umc.edison.remote.model.mypage.UpdateProfileRequest
import com.umc.edison.remote.model.mypage.UpdateProfileResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface MyPageApiService {
    @GET("bubbles/deleted")
    suspend fun getDeletedBubbles(): ResponseWithPagination<GetDeletedBubbleListResponse>

    @GET("members")
    suspend fun getProfileInfo(): ResponseWithData<GetProfileInfoResponse>

    @PATCH("members/profile")
    suspend fun updateProfile(@Body profile: UpdateProfileRequest): ResponseWithData<UpdateProfileResponse>

    @GET("identity/{category}")
    suspend fun getTestKeyword(@Path("category") category: String): ResponseWithData<List<GetIdentityKeywordResponse>>

    @GET("members/identity")
    suspend fun getAllMyTestResults(): ResponseWithData<GetAllMyTestResultsResponse>

    @PATCH("members/identity")
    suspend fun updateTest(@Body identity: UpdateTestRequest): ResponseWithData<UpdateTestResponse>

    @POST("members/logout")
    suspend fun logout() : BaseResponse

    @DELETE("members/cancel")
    suspend fun deleteAccount() : BaseResponse
}