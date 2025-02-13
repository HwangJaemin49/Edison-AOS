package com.umc.edison.data.model

import com.umc.edison.domain.model.Cluster

data class ClusteredBubblesEntity(
    val groupId: Int,
    val centerX: Float,
    val centerY: Float,
    val radius: Float,
) : DataMapper<Cluster> {
    override fun toDomain(): Cluster = Cluster(
        groupId = groupId,
        centerX = centerX,
        centerY = centerY,
        radius = radius,
    )
}
