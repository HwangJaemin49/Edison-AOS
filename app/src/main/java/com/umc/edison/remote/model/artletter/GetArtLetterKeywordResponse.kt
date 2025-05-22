package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.artLetter.ArtLetterKeyWordEntity
import com.umc.edison.remote.model.RemoteMapper

class GetArtLetterKeywordResponse (
    @SerializedName("artletterId") val artletterId: Int,
    @SerializedName("keyword") val keyword: String
) : RemoteMapper<ArtLetterKeyWordEntity> {
    override fun toData(): ArtLetterKeyWordEntity = ArtLetterKeyWordEntity (
        artLetterId = artletterId,
        keyword = keyword
    )
}