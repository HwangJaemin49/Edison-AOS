package com.umc.edison.remote.api

import com.umc.edison.remote.model.ResponseWithData
import com.umc.edison.remote.model.bubble.AddBubbleRequest
import com.umc.edison.remote.model.bubble.AddBubbleResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BubbleApiService {
    @POST("/bubbles")
    suspend fun addBubble(@Body bubble: AddBubbleRequest): ResponseWithData<AddBubbleResponse>
}