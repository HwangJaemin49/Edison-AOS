package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName

data class PostEditorPickArtLetterRequest(
    @SerializedName("artletterIds") val artletterIds: List<Int>,
)