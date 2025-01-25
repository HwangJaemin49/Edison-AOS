package com.umc.edison.remote.api

import com.umc.edison.remote.model.GetLabelResponse
import com.umc.edison.remote.model.ResponseWithData
import retrofit2.http.GET

interface BubbleSpaceApiService {
    @GET("labels")
    suspend fun getAllLabels(): ResponseWithData<List<GetLabelResponse>>
}