package com.umc.edison.data.model

import com.umc.edison.domain.model.Cluster

data class PositionedGroupEntity(
    val groupId: Int,
    val x: Float,
    val y: Float,
    val radius: Float
) : DataMapper<Cluster> {
    override fun toDomain(): Cluster = Cluster(
        groupId = groupId,
        centerX = x,
        centerY = y,
        radius = radius
    )
}
