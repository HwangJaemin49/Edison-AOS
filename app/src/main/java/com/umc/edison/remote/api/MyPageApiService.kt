package com.umc.edison.remote.api

import com.umc.edison.remote.model.GetDeletedBubbleListResponse
import com.umc.edison.remote.model.ResponseWithPagination
import retrofit2.http.GET

interface MyPageApiService {
    @GET("bubbles/deleted")
    suspend fun getDeletedBubbles(): ResponseWithPagination<GetDeletedBubbleListResponse>
}