package com.umc.edison.presentation.model

import androidx.compose.ui.geometry.Offset
import com.umc.edison.domain.model.Cluster

data class ClusterModel(
    val groupId: Int,
    val position: Offset,
    val radius: Float,
)

fun Cluster.toPresentation(): ClusterModel {
    val scale = 400f

    return ClusterModel(
        groupId = groupId,
        position = Offset(centerX * scale, centerY * scale),
        radius = radius * scale,
    )
}

fun List<Cluster>.toPresentation(): List<ClusterModel> {
    return map { it.toPresentation() }
}
