package com.umc.edison.ui.space

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.umc.edison.presentation.space.BubbleGraphViewModel
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray800

@Composable
fun SpaceTabScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BubbleGraphScreen()
    }
}

@Composable
fun BubbleGraphScreen(
    viewModel: BubbleGraphViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    LaunchedEffect(Unit) { viewModel.fetchClusteredBubbles() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(0.5f, 3f) // 확대/축소 범위 제한
                    offset += pan
                }
            }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
        ) {
            uiState.edges.forEach { edge ->
                val start = uiState.bubbles.find { it.bubble.id == edge.startBubbleId }?.position
                    ?: Offset.Zero
                val end = uiState.bubbles.find { it.bubble.id == edge.endBubbleId }?.position
                    ?: Offset.Zero
                drawLine(color = Gray300, start = start, end = end, strokeWidth = 1.dp.toPx())
            }

            val radius = 12f
            uiState.bubbles.forEach { positionedBubble ->
                val colors: List<Color> = positionedBubble.bubble.labels.map { it.color }
                Log.d("BubbleGraphScreen", "bubble: ${positionedBubble.bubble.title}, colors: $colors")

                if (colors.size <= 1) {
                    drawCircle(
                        color = colors.firstOrNull() ?: Gray100,
                        radius = radius,
                        center = positionedBubble.position
                    )
                } else {
                    drawCircle(
                        // linear gradient (bubble이 갖고있는 라벨의 색상 별로)
                        brush = Brush.linearGradient(
                            colors,
                            start = positionedBubble.position - Offset(radius * 2, 0f),
                            end = positionedBubble.position + Offset(radius * 2, 0f),
                        ),
                        radius = radius,
                        center = positionedBubble.position
                    )
                }
                drawContext.canvas.nativeCanvas.drawText(
                    positionedBubble.bubble.title
                        ?: positionedBubble.bubble.contentBlocks.firstOrNull()?.content?.take(10)
                        ?: "No Contents",
                    positionedBubble.position.x,
                    positionedBubble.position.y + radius.dp.toPx() + 10.dp.toPx(),
                    Paint().apply {
                        color = Gray800.toArgb()
                        textSize = 35f
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }
    }
}
