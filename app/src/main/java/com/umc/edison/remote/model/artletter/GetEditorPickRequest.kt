package com.umc.edison.remote.model.artletter

import com.google.gson.annotations.SerializedName

data class GetEditorPickRequest(
    @SerializedName("artletterIds") val ids: List<Int>
)
