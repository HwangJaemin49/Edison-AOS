package com.umc.edison.presentation.model

data class EdgeDataModel(
    val startBubbleId: Int,
    val endBubbleId: Int
)

fun List<PositionedBubbleModel>.toEdgeDataModel(): List<EdgeDataModel> {
    val edges = mutableListOf<EdgeDataModel>()
    val bubbles = this.sortedByDescending { it.bubble.date }

    bubbles.forEach { bubblePosition ->
        bubblePosition.bubble.linkedBubble?.let {
            edges.add(
                EdgeDataModel(
                    startBubbleId = bubblePosition.bubble.id,
                    endBubbleId = it.id
                )
            )
        }

        bubblePosition.bubble.backLinks.forEach { backLink ->
            edges.add(
                EdgeDataModel(
                    startBubbleId = bubblePosition.bubble.id,
                    endBubbleId = backLink.id
                )
            )
        }
    }

    // edges의 중복 제거 (startBubbleId, endBubbleId가 같은 경우 혹은 쌍을 뒤집은 경우 같으면 중복 제거)
    val uniqueEdges = edges.distinctBy { edge ->
        if (edge.startBubbleId < edge.endBubbleId) {
            edge
        } else {
            edge.copy(
                startBubbleId = edge.endBubbleId,
                endBubbleId = edge.startBubbleId
            )
        }
    }

    return uniqueEdges
}
