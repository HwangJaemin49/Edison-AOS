package com.umc.edison.remote.api

import com.umc.edison.remote.model.bubble_space.GetAllBubblesResponse
import com.umc.edison.remote.model.bubble_space.GetLabelDetailResponse
import com.umc.edison.remote.model.bubble_space.GetLabelResponse
import com.umc.edison.remote.model.ResponseWithData
import com.umc.edison.remote.model.ResponseWithPagination
import retrofit2.http.GET
import retrofit2.http.Path

interface BubbleSpaceApiService {
    @GET("labels")
    suspend fun getAllLabels(): ResponseWithData<List<GetLabelResponse>>

    @GET("labels/{labelId}")
    suspend fun getLabelDetail(@Path("labelId") labelId: Int): ResponseWithData<GetLabelDetailResponse>

    @GET("/bubbles/space")
    suspend fun getAllBubbles(): ResponseWithPagination<GetAllBubblesResponse>
}