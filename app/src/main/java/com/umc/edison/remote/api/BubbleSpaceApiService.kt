package com.umc.edison.remote.api

import com.umc.edison.remote.model.GetLabelDetailResponse
import com.umc.edison.remote.model.GetLabelResponse
import com.umc.edison.remote.model.ResponseWithData
import retrofit2.http.GET
import retrofit2.http.Path

interface BubbleSpaceApiService {
    @GET("labels")
    suspend fun getAllLabels(): ResponseWithData<List<GetLabelResponse>>

    @GET("labels/{labelId}")
    suspend fun getLabelDetail(@Path("labelId") labelId: Int): ResponseWithData<GetLabelDetailResponse>
}