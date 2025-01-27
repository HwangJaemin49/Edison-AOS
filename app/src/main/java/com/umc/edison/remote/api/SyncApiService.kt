package com.umc.edison.remote.api

import com.umc.edison.remote.model.AddLabelRequest
import com.umc.edison.remote.model.ResponseWithData
import retrofit2.http.Body
import retrofit2.http.POST

interface SyncApiService {
    @POST("labels")
    suspend fun addLabel(@Body label: AddLabelRequest): ResponseWithData<AddLabelRequest>
}