package com.umc.edison.remote.api

import com.umc.edison.remote.model.ResponseWithData
import com.umc.edison.remote.model.ResponseWithListData
import com.umc.edison.remote.model.ResponseWithPagination
import com.umc.edison.remote.model.artletter.GetAllArtLettersResponse
import com.umc.edison.remote.model.artletter.GetArtLetterDetailResponse
import com.umc.edison.remote.model.artletter.GetArtLetterKeywordResponse
import com.umc.edison.remote.model.artletter.GetEditorPickRequest
import com.umc.edison.remote.model.artletter.GetSortedArtLettersResponse
import com.umc.edison.remote.model.artletter.PostArtLetterLikeResponse
import com.umc.edison.remote.model.artletter.PostArtLetterScrapResponse
import com.umc.edison.remote.model.artletter.GetEditorPickResponse
import com.umc.edison.remote.model.artletter.GetSearchArtLettersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtLetterApiService {
    @GET("/artletters")
    suspend fun getAllArtLetters(): ResponseWithPagination<GetAllArtLettersResponse>

    @GET("/artletters")
    suspend fun getSortedArtLetters(@Query("sortType") sortBy: String): ResponseWithListData<GetSortedArtLettersResponse>

    @POST("/artletters/{artletterId}/scrap")
    suspend fun postArtLetterScrap(@Path("artletterId") id: Int): ResponseWithData<PostArtLetterScrapResponse>

    @POST("/artletters/{artletterId}/like")
    suspend fun postArtLetterLike(@Path("artletterId") id: Int): ResponseWithData<PostArtLetterLikeResponse>

    @POST("/artletters/editor-pick")
    suspend fun getEditorPick(@Body ids: GetEditorPickRequest): ResponseWithListData<GetEditorPickResponse>

    @GET("/artletters/{letterId}")
    suspend fun getArtLetterDetail(@Path("letterId") letterId: Int): ResponseWithData<GetArtLetterDetailResponse>

    @GET("/artletters/recommend-bar/keyword")
    suspend fun getRecommendedKeywords(@Query("artletterIds") artletterIds: List<Int>): ResponseWithListData<GetArtLetterKeywordResponse>

    @GET("/artletters/search")
    suspend fun getSearchArtLetters(@Query("keyword") keyword: String, @Query("sortType") sortType: String): ResponseWithPagination<GetSearchArtLettersResponse>

    // TODO: api endpoint 추가
    suspend fun removeRecentSearch(keyword: String)

    // TODO: api endpoint 추가
    suspend fun getRecentSearches(): ResponseWithData<List<String>>

}