package com.umc.edison.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.ui.theme.White000

@Composable
fun BubblesLayout(
    bubbles: List<BubbleModel>,
    onBubbleClick: (BubbleModel) -> Unit,
    onBubbleLongClick: (BubbleModel) -> Unit = {},
    isBlur: Boolean = false,
    selectedBubble: List<BubbleModel> = emptyList(),
    searchKeyword: String = "",
    isReversed: Boolean = false
) {
    val bubbleOffsets = remember { mutableStateMapOf<BubbleModel, Pair<Dp, Dp>>() }
    val totalYOffset = remember { mutableStateOf(0.dp) }
    val placedBubbles = remember { mutableStateListOf<PlacedBubble>() }
    val scrollState = rememberScrollState()

    LaunchedEffect(bubbles.size) {
        if (isReversed && bubbles.isNotEmpty()) {
            snapshotFlow { scrollState.maxValue }
                .collect { maxValue ->
                    scrollState.scrollTo(maxValue)
                }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(scrollState)
    ) {
        val verticalPadding = if (isReversed) 50.dp else 0.dp

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(totalYOffset.value + verticalPadding)
                .padding(vertical = verticalPadding)
        ) {
            bubbles.forEach { bubble ->
                val bubbleSize = calculateBubblePreviewSize(bubble)

                val (xOffset, yOffset) = bubbleOffsets.getOrPut(bubble) {
                    calculateOffset(
                        bubbleSize = bubbleSize,
                        placedBubbles = placedBubbles,
                        accumulatedYOffset = totalYOffset.value
                    )
                }
                totalYOffset.value = maxOf(totalYOffset.value, yOffset + bubbleSize.size)

                placedBubbles.add(
                    PlacedBubble(
                        x = xOffset,
                        y = yOffset,
                        size = bubbleSize.size
                    )
                )

                Box(
                    modifier = Modifier
                        .size(bubbleSize.size)
                        .offset(x = xOffset, y = yOffset)
                ) {
                    BubblePreview(
                        bubble = bubble,
                        size = bubbleSize,
                        onClick = { onBubbleClick(bubble) },
                        onLongClick = { onBubbleLongClick(bubble) },
                        searchKeyword = searchKeyword
                    )

                    if (isBlur && !selectedBubble.contains(bubble)) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(White000.copy(alpha = 0.5f))
                                .graphicsLayer {
                                    renderEffect = BlurEffect(
                                        radiusX = 16.dp.toPx(),
                                        radiusY = 16.dp.toPx()
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}

data class PlacedBubble(
    val x: Dp,
    val y: Dp,
    val size: Dp
)

enum class PositionGroup { LEFT, CENTER, RIGHT }

@Composable
private fun calculateOffset(
    bubbleSize: BubbleType.BubbleSize,
    placedBubbles: List<PlacedBubble>,
    accumulatedYOffset: Dp
): Pair<Dp, Dp> {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val padding = 10.dp

    val minX = padding
    val maxX = screenWidthDp - bubbleSize.size - padding
    val leftX = (maxX - minX) / 3 + minX
    val rightX = (maxX - minX) / 3 * 2 + minX

    val previous = placedBubbles.lastOrNull()
    if (previous == null) {
        return minX to 0.dp
    }

    val positionGroup = when {
        previous.x < leftX -> PositionGroup.LEFT
        previous.x < rightX -> PositionGroup.CENTER
        else -> PositionGroup.RIGHT
    }

    val xCandidates = when (positionGroup) {
        PositionGroup.LEFT -> listOf(listOf(rightX, maxX), listOf(leftX, rightX), listOf(minX, leftX))
        PositionGroup.CENTER -> listOf(listOf(minX, leftX), listOf(rightX, maxX), listOf(leftX, rightX))
        PositionGroup.RIGHT -> listOf(listOf(minX, leftX), listOf(leftX, rightX), listOf(rightX, maxX))
    }

    val random = kotlin.random.Random
    val baseYOffset = accumulatedYOffset + -(bubbleSize.size / 2)
    val step = 10.dp
    val candidateOffsets = mutableListOf<Pair<Dp, Dp>>()

    for (xRange in xCandidates) {
        repeat(5) {
            val trialX = (xRange[0].value.toInt()..xRange[1].value.toInt()).random(random).dp

            var trialYOffset = baseYOffset

            while (trialYOffset < accumulatedYOffset) {
                val trialBubble = PlacedBubble(
                    x = trialX,
                    y = trialYOffset,
                    size = bubbleSize.size
                )

                val overlapExists = placedBubbles.any { placed ->
                    isOverlapping(trialBubble, placed)
                }

                if (!overlapExists) {
                    candidateOffsets.add(trialX to trialYOffset)
                }

                trialYOffset += step
            }
        }
    }

    val screenMidX = screenWidthDp / 2
    val fallbackXOffset = if (previous.x > screenMidX) {
        padding
    } else {
        maxX
    }

    return candidateOffsets.minByOrNull { it.second.value }
        ?: (fallbackXOffset to accumulatedYOffset)
}

private fun isOverlapping(b1: PlacedBubble, b2: PlacedBubble): Boolean {
    val distanceX = (b1.x.value + b1.size.value / 2) - (b2.x.value + b2.size.value / 2)
    val distanceY = (b1.y.value + b1.size.value / 2) - (b2.y.value + b2.size.value / 2)
    val distance = kotlin.math.sqrt(distanceX * distanceX + distanceY * distanceY)

    return distance < (b1.size.value + b2.size.value) / 2 + 10.dp.value
}
