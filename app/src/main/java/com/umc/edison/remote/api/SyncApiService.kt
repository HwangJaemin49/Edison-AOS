package com.umc.edison.remote.api

import com.umc.edison.remote.model.SyncLabelRequest
import com.umc.edison.remote.model.ResponseWithData
import retrofit2.http.Body
import retrofit2.http.POST

interface SyncApiService {
    @POST("labels/sync")
    suspend fun syncLabel(@Body label: SyncLabelRequest): ResponseWithData<SyncLabelRequest>
}