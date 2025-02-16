package com.umc.edison.remote.model.artletter

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.ArtletterEntity
import com.umc.edison.remote.model.RemoteMapper

data class GetAllArtLettersResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("likes") val likes: Int,
    @SerializedName("scraps") val scraps: Int,
) : RemoteMapper<ArtletterEntity> {

    override fun toData(): ArtletterEntity {
        // 로그 추가: 데이터 변환 전 출력
        Log.d("Mapping", "GetAllArtLettersResponse -> ArtletterEntity 변환 시작: id=$id, title=$title, likes=$likes, scraps=$scraps")

        // 변환 작업
        return ArtletterEntity(
            artletterId = id,
            title = title,
            thumbnail = thumbnail,
            likes = likes,
            scraps = scraps,
        ).also {
            // 변환 후 로그 추가
            Log.d("Mapping", "변환 완료: artletterId=${it.artletterId}, title=${it.title}")
        }
    }
}
