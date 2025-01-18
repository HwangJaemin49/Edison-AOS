package com.umc.edison.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.umc.edison.R
import com.umc.edison.ui.theme.White000

@Composable
fun ColorPalette(
    colors: List<Color>,
    onColorSelected: (Color) -> Unit
) {
    val bitmap =
        BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.img_color_palette)

    val originalWidth = bitmap.width
    val originalHeight = bitmap.height

    var displayedWidth by remember { mutableIntStateOf(0) }
    var displayedHeight by remember { mutableIntStateOf(0) }

    var dragPosition by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(22.dp)
            .background(White000),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Image to extract colors",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        displayedWidth = coordinates.size.width
                        displayedHeight = coordinates.size.height
                    }
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            val newPosition = dragPosition + Offset(dragAmount.x, dragAmount.y)

                            dragPosition = newPosition.coerceIn(
                                Offset.Zero,
                                Offset(displayedWidth.toFloat(), displayedHeight.toFloat())
                            )

                            val pixelX = (dragPosition.x / displayedWidth * originalWidth).toInt()
                            val pixelY = (dragPosition.y / displayedHeight * originalHeight).toInt()

                            if (pixelX in 0 until originalWidth && pixelY in 0 until originalHeight) {
                                val pixelColor = bitmap.getPixel(pixelX, pixelY)
                                onColorSelected(Color(pixelColor))
                            }
                            change.consume()
                        }
                    }
            )

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .offset {
                        IntOffset(
                            (dragPosition.x - 14.dp.toPx()).toInt(),
                            (dragPosition.y - 14.dp.toPx()).toInt()
                        )
                    }
                    .background(color = Color.Transparent, shape = CircleShape)
                    .border(2.dp, White000, shape = CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(11.dp),
        ) {
            colors.chunked(6).forEach { rowColors ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowColors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(13.dp))
                                .background(color)
                                .clickable {
                                    onColorSelected(color)
                                }
                        )
                    }
                }
            }
        }
    }
}

private fun Offset.coerceIn(min: Offset, max: Offset): Offset {
    return Offset(
        x = x.coerceIn(min.x, max.x),
        y = y.coerceIn(min.y, max.y)
    )
}
