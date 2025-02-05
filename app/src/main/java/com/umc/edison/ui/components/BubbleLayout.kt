package com.umc.edison.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
    onBubbleLongClick: (BubbleModel) -> Unit,
    isBlur: Boolean = false,
    selectedBubble: List<BubbleModel>,
) {
    val bubbleOffsets = remember { mutableStateMapOf<BubbleModel, Dp>() }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(bubbles.size) { index ->
            val bubble = bubbles[index]

            val xOffset = bubbleOffsets.getOrPut(bubble) {
                calculateInitialBubbleXOffset(bubble)
            }

            var bubbleSize = calculateBubbleSize(bubble)

            if (bubbleSize == BubbleType.BubbleMain) {
                bubbleSize = BubbleType.Bubble300
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = xOffset)
                    .size(bubbleSize.size + 4.dp)
            ) {
                BubblePreview(
                    bubble = bubble,
                    size = bubbleSize,
                    onClick = { onBubbleClick(bubble) },
                    onLongClick = { onBubbleLongClick(bubble) }
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

@Composable
private fun calculateInitialBubbleXOffset(bubble: BubbleModel): Dp {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val padding = 8.dp
    var bubbleSize = calculateBubbleSize(bubble)

    if (bubbleSize == BubbleType.BubbleMain) {
        bubbleSize = BubbleType.Bubble300
    }

    val maxXOffset = screenWidthDp - bubbleSize.size - padding

    // 랜덤 초기 오프셋 계산
    return (padding.value.toInt()..maxXOffset.value.toInt()).random().dp
}
