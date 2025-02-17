package com.umc.edison.presentation.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.sqrt

data class ClusterModel(
    val groupId: Int,
    val position: Offset,
    val radius: Float,
    val colors: List<Color>,
)

fun List<PositionedBubbleModel>.toClusterModel(): List<ClusterModel> {
    return this.groupBy { it.groupId }.map { (groupId, bubblePositions) ->
        // 해당 그룹에 속한 모든 버블 위치에서 최소/최대 좌표 구하기
        val minX = bubblePositions.minOf { it.position.x }
        val maxX = bubblePositions.maxOf { it.position.x }
        val minY = bubblePositions.minOf { it.position.y }
        val maxY = bubblePositions.maxOf { it.position.y }

        // 바운딩 박스의 중심을 원의 중심으로 사용
        val center = Offset((minX + maxX) / 2, (minY + maxY) / 2)

        // 그룹 내 모든 버블 위치 중, 중심에서 가장 멀리 있는 점까지의 거리 계산
        val radius = bubblePositions.maxOf { pos ->
            val dx = pos.position.x - center.x
            val dy = pos.position.y - center.y
            sqrt(dx * dx + dy * dy)
        }

        val colors = bubblePositions
            .flatMap { it.bubble.labels.map { label -> label.color } }
            .distinct()

        ClusterModel(
            groupId = groupId,
            position = if (bubblePositions.size == 1) bubblePositions.first().position else center,
            radius = if (bubblePositions.size == 1) 100f else radius * 1.7f,
            colors = colors
        )
    }
}
