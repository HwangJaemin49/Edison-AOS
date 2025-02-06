package com.umc.edison.remote.api

import com.umc.edison.remote.model.ResponseWithPagination
import com.umc.edison.remote.model.artletter.GetAllArtLettersResponse
import retrofit2.http.GET

interface ArtLetterApiService {
    @GET("/artletters")
    suspend fun getAllArtLetters(): ResponseWithPagination<GetAllArtLettersResponse>
}
