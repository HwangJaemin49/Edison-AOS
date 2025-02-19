package com.umc.edison.remote.api

import com.umc.edison.remote.model.sync.SyncLabelRequest
import com.umc.edison.remote.model.ResponseWithData
import com.umc.edison.remote.model.ResponseWithPagination
import com.umc.edison.remote.model.space.GetAllBubblesResponse
import com.umc.edison.remote.model.space.GetLabelResponse
import com.umc.edison.remote.model.sync.SyncBubbleRequest
import com.umc.edison.remote.model.sync.SyncBubbleResponse
import com.umc.edison.remote.model.sync.SyncLabelResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SyncApiService {
    @POST("labels/sync")
    suspend fun syncLabel(@Body label: SyncLabelRequest): ResponseWithData<SyncLabelResponse>

    @POST("bubbles/sync")
    suspend fun syncBubble(@Body bubble: SyncBubbleRequest): ResponseWithData<SyncBubbleResponse>

    @GET("/bubbles/space")
    suspend fun getAllBubbles(): ResponseWithPagination<GetAllBubblesResponse>

    @GET("labels")
    suspend fun getAllLabels(): ResponseWithData<List<GetLabelResponse>>
}