package com.umc.edison.remote.api


import com.umc.edison.remote.model.ResponseWithData
import com.umc.edison.remote.model.ResponseWithListData
import com.umc.edison.remote.model.ResponseWithPagination
import com.umc.edison.remote.model.artletter.GetAllArtLettersResponse
import com.umc.edison.remote.model.artletter.GetArtLetterDetailResponse
import com.umc.edison.remote.model.artletter.GetSortedArtLettersResponse
import com.umc.edison.remote.model.artletter.PostArtLetterLikeResponse
import com.umc.edison.remote.model.artletter.PostEditorPickArtLetterResponse
import com.umc.edison.remote.model.artletter.ScrapArtLettersResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtLetterApiService {
    @GET("/artletters")
    suspend fun getAllArtLetters(): ResponseWithPagination<GetAllArtLettersResponse>

    @GET("/artletters/sorted")
    suspend fun getSortedArtLetters(@Query("sortBy") sortBy: String): ResponseWithListData<GetSortedArtLettersResponse>

    @POST("/artletters/{artletterId}/scrap")
    suspend fun toggleScrap(@Path("artletterId") artletterId: Int): ScrapArtLettersResult

    @POST("/artletters/{artletterId}/like")
    suspend fun postArtLetterLike(@Path("artletterId") artletterId: Int): ResponseWithData<PostArtLetterLikeResponse>

    @POST("/artletters/editor-pick")
    suspend fun postEditorPick(@Body artletterIds: List<Int>): ResponseWithListData<PostEditorPickArtLetterResponse>

    @GET("/artletters/{letterId}")
    suspend fun getArtLetterDetail(@Path("letterId") letterId: Int): ResponseWithData<GetArtLetterDetailResponse>

    @GET("/artletters/recommend-bar/keyword")
    suspend fun getRecommendedKeywords(@Query("artletterIds") artletterIds: String): ResponseWithListData<GetArtLetterKeyword>

}