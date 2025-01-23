package com.umc.edison.ui.label

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.umc.edison.ui.theme.EdisonTheme

@Composable
fun BubbleZoomTestScreen() {
    val scaleState = remember { mutableFloatStateOf(0.1f) } // 초기 배율

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ ->
                    scaleState.floatValue = (scaleState.floatValue * zoom).coerceIn(0.1f, 5f) // 배율 제한
                }
            }
    ) {
        BubbleZoom(scale = scaleState.floatValue, color = Color.Blue)
    }
}

@Composable
fun BubbleZoom(scale: Float, color: Color) {
    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(size.width / 2, size.height / 2)
        when {
            scale < 0.3f -> {
                // 점 모양
                drawCircle(color = color, radius = 10f * scale * 10, center = center)
            }
            else -> {
                // 버블 모양
                drawCircle(color = color, radius = 100f * scale, center = center)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBubbleZoomTestScreen() {
    EdisonTheme {
        BubbleZoomTestScreen()
    }
}
