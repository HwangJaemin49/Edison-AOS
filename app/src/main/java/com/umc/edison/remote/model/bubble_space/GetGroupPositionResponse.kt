package com.umc.edison.remote.model.bubble_space

import com.google.gson.annotations.SerializedName
import com.umc.edison.data.model.PositionedGroupEntity
import com.umc.edison.remote.model.RemoteMapper

data class GetGroupPositionResponse(
    @SerializedName("groupId") val groupId: Int,
    @SerializedName("centerX") val x: Float,
    @SerializedName("centerY") val y: Float,
    @SerializedName("radius") val radius: Float
) : RemoteMapper<PositionedGroupEntity> {
    override fun toData(): PositionedGroupEntity = PositionedGroupEntity(
        groupId = groupId,
        x = x,
        y = y,
        radius = radius
    )
}
