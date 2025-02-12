package com.umc.edison.remote.api


import com.umc.edison.remote.model.ResponseWithListData
import com.umc.edison.remote.model.ResponseWithPagination
import com.umc.edison.remote.model.artletter.GetAllArtLettersResponse
import com.umc.edison.remote.model.artletter.GetSortedArtLettersResponse
import com.umc.edison.remote.model.artletter.PostEditorPickArtLetterResponse
import com.umc.edison.remote.model.artletter.ScrapArtLettersResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ArtLetterApiService {
    @GET("/artletters")
    suspend fun getAllArtLetters(): ResponseWithPagination<GetAllArtLettersResponse>

    @GET("/artletters/sorted")
    suspend fun getSortedArtLetters(@Body sortBy: String): ResponseWithListData<GetSortedArtLettersResponse>

    @POST("/artletters/{artletterId}/scrap")
    suspend fun toggleScrap(@Path("artletterId") artletterId: Int): ScrapArtLettersResult

    @POST("/artletters/editor-pick")
    suspend fun postEditorPick(@Body artletterIds: List<Int>): ResponseWithListData<PostEditorPickArtLetterResponse>

}