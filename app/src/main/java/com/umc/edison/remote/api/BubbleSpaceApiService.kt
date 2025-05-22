package com.umc.edison.remote.api

import com.umc.edison.remote.model.ResponseWithData
import com.umc.edison.remote.model.space.GetBubblePositionResponse
import retrofit2.http.GET

interface BubbleSpaceApiService {
    @GET("/spaces/convert")
    suspend fun getBubblePosition(): ResponseWithData<List<GetBubblePositionResponse>>
}