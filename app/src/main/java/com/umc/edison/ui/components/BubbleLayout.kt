package com.umc.edison.ui.components

import android.util.Log
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.ui.theme.White000

private const val TAG = "BubbleLayoutDebug"

@Composable
fun BubblesLayout(
    bubbles: List<BubbleModel>,
    onBubbleClick: (BubbleModel) -> Unit,
    onBubbleLongClick: (BubbleModel) -> Unit = {},
    isBlur: Boolean = false,
    selectedBubble: List<BubbleModel> = emptyList(),
    searchKeyword: String = "",
    isReversed: Boolean = false,
    setGlobalBubblePosition: (Offset, IntSize) -> Unit = { _, _ -> },
) {
    val bubbleOffsets = remember { mutableStateMapOf<BubbleModel, Pair<Dp, Dp>>() }
    val totalYOffset = remember { mutableStateOf(0.dp) }
    val placedBubbles = remember { mutableStateListOf<PlacedBubble>() }
    val scrollState = rememberScrollState()

    Log.d(TAG, "Bubble list size: ${bubbles.size}")

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
            bubbles.forEachIndexed { idx, bubble ->
                val bubbleSize = calculateBubblePreviewSize(bubble)
                Log.d(TAG, "[$idx] Bubble size: ${bubbleSize.size}")

                val (xOffset, yOffset) = bubbleOffsets.getOrPut(bubble) {
                    val offset = calculateOffset(
                        bubbleSize = bubbleSize,
                        placedBubbles = placedBubbles,
                        accumulatedYOffset = totalYOffset.value
                    )
                    Log.d(TAG, "[$idx] Calculated offset for bubble: $offset")
                    offset
                }

                totalYOffset.value = maxOf(totalYOffset.value, yOffset + bubbleSize.size)
                Log.d(TAG, "[$idx] Updated totalYOffset: ${totalYOffset.value}")

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
                        searchKeyword = searchKeyword,
                        modifier = Modifier.onGloballyPositioned { coordinates ->
                            if (idx == bubbles.size / 2) {
                                val position = coordinates.positionOnScreen()
                                val size = coordinates.size
                                Log.d(TAG, "[$idx] Middle bubble position: $position, size: $size")
                                setGlobalBubblePosition(position, size)
                            }
                        }
                    )

                    if (isBlur && !selectedBubble.contains(bubble)) {
                        Log.d(TAG, "[$idx] Applying blur to bubble")
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

    val positionGroup = when {
        previous == null -> PositionGroup.LEFT
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
    val baseYOffset = max(accumulatedYOffset - (bubbleSize.size / 2), 0.dp)
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
                    val result = isOverlapping(trialBubble, placed)
                    if (result) {
                        Log.d(TAG, "Overlap found at x=${trialX.value}, y=${trialYOffset.value}")
                    }
                    result
                }

                if (!overlapExists) {
                    Log.d(TAG, "Valid offset: x=${trialX.value}, y=${trialYOffset.value}")
                    candidateOffsets.add(trialX to trialYOffset)
                }

                trialYOffset += step
            }
        }
    }

    val screenMidX = screenWidthDp / 2
    val fallbackXOffset = if ((previous?.x ?: minX) > screenMidX) padding else maxX

    if (candidateOffsets.isEmpty()) {
        Log.w(TAG, "No valid candidateOffsets found, falling back to x=${fallbackXOffset.value}")
    }

    return candidateOffsets.minByOrNull { it.second.value }
        ?: (fallbackXOffset to accumulatedYOffset)
}

private fun isOverlapping(b1: PlacedBubble, b2: PlacedBubble): Boolean {
    val distanceX = (b1.x.value + b1.size.value / 2) - (b2.x.value + b2.size.value / 2)
    val distanceY = (b1.y.value + b1.size.value / 2) - (b2.y.value + b2.size.value / 2)
    val distance = kotlin.math.sqrt(distanceX * distanceX + distanceY * distanceY)
    val threshold = (b1.size.value + b2.size.value) / 2 + 10.dp.value

    val overlap = distance < threshold
    if (overlap) {
        Log.d(TAG, "isOverlapping: TRUE | d=$distance < threshold=$threshold")
    }
    return overlap
}
