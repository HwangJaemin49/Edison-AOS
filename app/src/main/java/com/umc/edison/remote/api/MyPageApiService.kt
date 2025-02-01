package com.umc.edison.remote.api

import com.umc.edison.remote.model.BaseResponse
import com.umc.edison.remote.model.ResponseWithData
import com.umc.edison.remote.model.ResponseWithPagination
import com.umc.edison.remote.model.mypage.GetDeletedBubbleListResponse
import com.umc.edison.remote.model.mypage.GetIdentityKeywordResponse
import com.umc.edison.remote.model.mypage.GetAllMyTestResultsResponse
import com.umc.edison.remote.model.mypage.GetMyScrapArtLettersResponse
import com.umc.edison.remote.model.mypage.UpdateIdentityRequest
import com.umc.edison.remote.model.mypage.UpdateIdentityResponse
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

    @DELETE("bubbles/trashbin/{bubbleId}")
    suspend fun deleteBubble(@Path("bubbleId") bubbleId: Int): BaseResponse

    @PATCH("members/profile")
    suspend fun updateProfile(@Body profile: UpdateProfileRequest): ResponseWithData<UpdateProfileResponse>

    @GET("identity/{category}")
    suspend fun getIdentityKeyword(@Path("category") category: String): ResponseWithData<List<GetIdentityKeywordResponse>>

    @GET("members/identity")
    suspend fun getAllMyTestResults(): ResponseWithData<GetAllMyTestResultsResponse>

    @GET("artletters/myscrap")
    suspend fun getMyScrapArtLetters(): ResponseWithPagination<GetMyScrapArtLettersResponse>

    @PATCH("members/identity")
    suspend fun updateIdentity(@Body identity: UpdateIdentityRequest): ResponseWithData<UpdateIdentityResponse>

    @POST("members/logout")
    suspend fun logout() : BaseResponse

    @DELETE("members/cancel")
    suspend fun deleteAccount() : BaseResponse
}