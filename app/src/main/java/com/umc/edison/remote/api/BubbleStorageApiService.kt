package com.umc.edison.remote.api

import com.umc.edison.remote.model.ResponseWithPagination
import com.umc.edison.remote.model.bubble_storage.GetStorageBubbleResponse
import retrofit2.http.GET

interface BubbleStorageApiService {
    @GET("/bubbles/recent")
    suspend fun getStorageBubbles(): ResponseWithPagination<GetStorageBubbleResponse>
}