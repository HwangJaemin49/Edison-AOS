package com.umc.edison.ui.space

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umc.edison.data.model.BubbleEntity
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray800

@Composable
fun SpaceTabScreen(
    navHostController: NavHostController,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "스페이스 화면")
    }
}

data class EdgeData(
    val startId: Int,
    val endId: Int
)

data class BubbleData(
    val id: Int,
    val label: String,
    val color: Color,
    val position: Offset
)

@Composable
fun BubbleGraphScreen(bubbles: List<BubbleData>, edges: List<EdgeData>) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

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
            edges.forEach { edge ->
                val start = bubbles.find { it.id == edge.startId }?.position ?: Offset.Zero
                val end = bubbles.find { it.id == edge.endId }?.position ?: Offset.Zero
                drawLine(color = Gray300, start = start, end = end, strokeWidth = 1.dp.toPx())
            }

            val radius = 12f
            bubbles.forEach { bubble ->
                drawCircle(
                    color = bubble.color,
                    radius = radius,
                    center = bubble.position
                )
                drawContext.canvas.nativeCanvas.drawText(
                    bubble.label,
                    bubble.position.x,
                    bubble.position.y + radius.dp.toPx() + 10.dp.toPx(),
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

@Preview(showBackground = true)
@Composable
fun BubbleGraphScreenPreview() {
    BubbleGraphScreen(
        bubbles = listOf(
            BubbleData(1, "A", Color.Red, Offset(0f, 0f)),
            BubbleData(2, "B", Color.Green, Offset(160f, 100f)),
            BubbleData(3, "C", Color.Blue, Offset(300f, 300f)),
        ),
        edges = listOf(
            EdgeData(1, 2),
            EdgeData(2, 3),
            EdgeData(3, 1),
        )
    )
}
