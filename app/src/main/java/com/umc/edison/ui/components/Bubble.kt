package com.umc.edison.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.umc.edison.domain.model.ContentType
import com.umc.edison.presentation.model.ContentBlockModel
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.theme.Aqua100
import com.umc.edison.ui.theme.EdisonTheme
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray400
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.Pretendard
import com.umc.edison.ui.theme.Red100
import com.umc.edison.ui.theme.White000
import com.umc.edison.ui.theme.Yellow100
import kotlin.math.cos
import kotlin.math.sin

/**
 * 마이 에디슨 메인 화면의 버블 입력 컴포저블
 */
@Composable
fun BubbleInput(
    onClick: () -> Unit,
    onSwipeUp: () -> Unit,
) {
    val bubbleSize = BubbleType.BubbleMain
    val canvasSize = bubbleSize.size

    var offsetY by remember { mutableFloatStateOf(0f) }
    val animatedOffsetY by animateFloatAsState(targetValue = offsetY)

    Box(
        modifier = Modifier
            .size(canvasSize)
            .clip(CircleShape)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { change, dragAmount ->
                        change.consume()
                        if (dragAmount < 0) {
                            offsetY += dragAmount
                            if (offsetY < -200f) {
                                onSwipeUp()
                                offsetY = 0f
                            }
                        }
                    },
                    onDragEnd = {
                        offsetY = 0f
                    }
                )
            }
            .clickable { onClick() }
            .offset {
                IntOffset(
                    x = 0,
                    y = animatedOffsetY.dp.roundToPx()
                )
            },
        contentAlignment = Alignment.Center
    ) {
        SingleBubble(bubbleSize = bubbleSize, color = Gray300)

        Box(
            modifier = Modifier.size(
                bubbleSize.textBoxSize.first.dp,
                bubbleSize.textBoxSize.second.dp
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "버블을 입력해주세요.",
                style = bubbleSize.fontStyle,
                color = Gray500,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * 버블 내용 확인 가능한 컴포저블
 */
@Composable
fun Bubble(
    bubble: BubbleModel,
    onClick: () -> Unit, // 편집 모드로 들어가는 클릭 리스너
) {
    val bubbleSize = calculateBubbleSize(bubble)

    if (checkBubbleContainImage(bubble) && bubbleSize == BubbleType.BubbleMain) {
        BubbleDoor(
            bubble = bubble,
            isEditable = false,
            onClick = onClick,
        )
    } else {
        TextContentBubble(
            bubble = bubble,
            colors = bubble.labels.map { it.color },
            onClick = onClick,
            bubbleSize = BubbleType.BubbleMain
        )
    }
}

/**
 * 버블 보관함 화면에서 사용되는 버블 미리보기 컴포저블
 */
@Composable
fun BubblePreview(
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    size: BubbleType.BubbleSize,
    bubble: BubbleModel
) {
    if (bubble.title != null || bubble.contentBlocks.firstOrNull()?.type == ContentType.TEXT) {
        TextContentBubble(
            bubble = bubble,
            colors = bubble.labels.map { it.color },
            onClick = onClick,
            onLongClick = onLongClick,
            bubbleSize = size
        )
    } else {
        // TODO: 이미지 1개를 배경으로 하는 버블 컴포저블
        ImageBubble(
            bubbleSize = size,
            imageUrl = bubble.mainImage ?: bubble.contentBlocks.firstOrNull()?.content ?: ""
        )
    }
}

@Composable
fun BubbleDoor(
    bubble: BubbleModel,
    isEditable: Boolean = false,
    onClick: () -> Unit,
) {
    val colors = bubble.labels.map { it.color }
    val outerColors = when (colors.size) {
        0 -> listOf(Color.White, Gray400)
        1 -> listOf(Color.White, colors[0])
        2 -> listOf(Color.White, colors[0], colors[1])
        3 -> listOf(Color.White, colors[0], colors[1], colors[2])
        else -> colors
    }

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null, // Ripple 효과 제거
                interactionSource = interactionSource
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        // Outer Canvas
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
        ) {
            // Outer Layer
            drawOuterGradientBubbleDoor(
                center = Offset(size.width / 2, size.height * 0.3f),
                width = size.width,
                height = size.height,
                colors = outerColors,
            )

            // Blur Layer
            drawBlurredOuterGradientBubbleDoor(
                center = Offset(size.width / 2, size.height * 0.3f),
                width = size.width,
                height = size.height,
            )
        }

        // Text Box
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .padding(start = 23.dp, top = 260.dp, end = 23.dp, bottom = 20.dp),
            contentAlignment = Alignment.TopStart
        ) {
            BubbleContent(bubble = bubble, isEditable = isEditable)
        }
    }
}

@Composable
private fun BubbleContent(
    bubble: BubbleModel,
    isEditable: Boolean = false,
) {
    var titleState by remember { mutableStateOf(bubble.title ?: "") }
    var contentBlocksState by remember { mutableStateOf(bubble.contentBlocks) }

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Title
        item {
            if (isEditable) {
                BasicTextField(
                    value = titleState,
                    onValueChange = { titleState = it },
                    textStyle = MaterialTheme.typography.displayMedium.copy(color = Gray800),
                    modifier = Modifier
                        .fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (titleState.isEmpty()) {
                                Text(
                                    text = "제목",
                                    style = MaterialTheme.typography.displayMedium.copy(color = Gray500),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            innerTextField()
                        }
                    }
                )
            } else {
                Text(
                    text = titleState,
                    style = MaterialTheme.typography.displayMedium,
                    color = Gray800,
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Content Blocks
        itemsIndexed(
            items = contentBlocksState,
            key = { _, contentBlock -> contentBlock.position }
        ) { index, contentBlock ->
            when (contentBlock.type) {
                ContentType.TEXT -> {
                    if (isEditable) {
                        BasicTextField(
                            value = contentBlock.content,
                            onValueChange = { newText ->
                                val updatedBlocks = contentBlocksState.toMutableList()
                                updatedBlocks[index] = contentBlock.copy(content = newText)
                                contentBlocksState = updatedBlocks
                            },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = Gray800
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (contentBlock.content.isEmpty() && contentBlocksState.size == 1) {
                                        Text(
                                            text = "내용을 입력해주세요.",
                                            style = MaterialTheme.typography.bodyMedium.copy(color = Gray500),
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    innerTextField()
                                }
                            }
                        )
                    } else {
                        Text(
                            text = contentBlock.content,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Gray800,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                ContentType.IMAGE -> {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(contentBlock.content)
                            .crossfade(true)
                            .scale(Scale.FILL)
                            .build()
                    )

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TextContentBubble(
    bubble: BubbleModel,
    colors: List<Color>,
    onClick: () -> Unit,
    onLongClick: () -> Unit= {},
    bubbleSize: BubbleType.BubbleSize,
) {
    val canvasSize = bubbleSize.size

    Box(
        modifier = Modifier
            .size(canvasSize)
            .clip(CircleShape)
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (bubble.mainImage != null) {
            ImageBubble(bubbleSize = bubbleSize, imageUrl = bubble.mainImage)
        } else {
            when (colors.size) {
                0 -> SingleBubble(bubbleSize = bubbleSize, color = Gray100)
                1 -> SingleBubble(bubbleSize = bubbleSize, color = colors[0])
                2 -> DoubleBubble(bubbleSize = bubbleSize, colors = colors)
                3 -> TripleBubble(bubbleSize = bubbleSize, colors = colors)
            }
        }

        Box(
            modifier = Modifier.size(
                bubbleSize.textBoxSize.first.dp,
                bubbleSize.textBoxSize.second.dp
            ),
            contentAlignment = Alignment.Center
        ) {
            val text = if (bubbleSize == BubbleType.BubbleMain) bubble.contentBlocks[0].content
            else bubble.title ?: bubble.contentBlocks[0].content

            Text(
                text = text,
                style = bubbleSize.fontStyle,
                color = Gray800,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ImageBubble(
    bubbleSize: BubbleType.BubbleSize,
    imageUrl: String
) {
    // TODO: 이미지 관련 정리되면 개발
}

@Composable
private fun SingleBubble(
    bubbleSize: BubbleType.BubbleSize,
    color: Color
) {
    Canvas(modifier = Modifier.size(bubbleSize.size)) {
        // Layer Blur 효과의 반지름
        val blurRadius = 60f
        val outerRadius = bubbleSize.size.toPx() / 2
        val innerRadius = bubbleSize.innerSize.toPx() / 2

        // 큰 원 그리기
        drawCircle(color = color, radius = outerRadius, center = center)

        // LayerBlur를 사용한 흰색 원
        drawLayerBlur(
            center = center,
            radius = innerRadius,
            colors = listOf(color),
            blurRadius = blurRadius,
        )
    }
}

@Composable
private fun DoubleBubble(
    bubbleSize: BubbleType.BubbleSize,
    colors: List<Color>,
) {
    require(colors.size == 2) { "DoubleBubble requires exactly two colors." }

    Canvas(modifier = Modifier.size(bubbleSize.size)) {
        // Layer Blur 효과의 반지름
        val blurRadius = 60f
        val outerRadius = bubbleSize.size.toPx() / 2
        val innerRadius = bubbleSize.innerSize.toPx() / 2

        // 큰 원 그리기
        drawOuterGradientCircle(
            center = center,
            radius = outerRadius,
            colors = colors
        )

        // 내부 원 그리기
        drawLayerBlur(
            center = center,
            radius = innerRadius,
            colors = colors,
            blurRadius = blurRadius
        )
    }
}

@Composable
private fun TripleBubble(
    bubbleSize: BubbleType.BubbleSize,
    colors: List<Color>,
) {
    Canvas(modifier = Modifier.size(bubbleSize.size)) {

        // Layer Blur 효과의 반지름
        val blurRadius = 60f
        val outerRadius = bubbleSize.size.toPx() / 2
        val innerRadius = bubbleSize.innerSize.toPx() / 2

        // 큰 원 그리기
        drawOuterGradientCircle(
            center = center,
            radius = outerRadius,
            colors = colors
        )

        // 2번째 레이어 블러 그리기
        drawLayerBlur(
            center = center,
            radius = innerRadius,
            colors = colors,
            blurRadius = blurRadius
        )

        // 내부 원 그리기
        clipPath(Path().apply {
            addOval(
                Rect(
                    center - Offset(outerRadius * 0.9f, outerRadius * 0.9f),
                    center + Offset(outerRadius, outerRadius),
                )
            )
        }) {
            val radius = innerRadius * 0.55f
            val blur = 200f
            val offsets = listOf(
                Offset(center.x + radius * 1.3f, center.y - radius * 0.2f),
                Offset(center.x - radius * 0.2f, center.y - radius * 0.2f),
                Offset(center.x + radius * 0.55f, center.y + radius * 1.55f)
            )

            drawCircleWithBlur(colors[1], offsets[1], radius, blur)
            drawCircleWithBlur(colors[0], offsets[0], radius, blur)
            drawCircleWithBlur(colors[2], offsets[2], radius, blur)
        }
    }
}

/**
 * 버블 내용에 이미지가 포함되어 있는지 확인하는 함수
 */
private fun checkBubbleContainImage(bubble: BubbleModel): Boolean {
    bubble.contentBlocks.forEach {
        if (it.type == ContentType.IMAGE) {
            return true
        }
    }
    return false
}

/**
 * 버블 텍스트 길이에 따른 사이즈 계산 함수
 */
fun calculateBubbleSize(bubble: BubbleModel): BubbleType.BubbleSize {
    var text = bubble.title ?: ""

    if (text.isEmpty() && bubble.contentBlocks.firstOrNull()?.type == ContentType.IMAGE) {
        return BubbleType.BubbleMain
    } else if (text.isEmpty()) {
        text = bubble.contentBlocks.firstOrNull()?.content ?: ""
    }

    fun calculateLineCount(text: String, textBoxWidthDp: Int, fontSizeSp: Float): Int {
        val charPerLine = (textBoxWidthDp / (fontSizeSp * 0.57)).toInt()

        val lines = text.split("\n").sumOf { line ->
            val lineLength = line.length
            val lineCount = lineLength / charPerLine
            if (lineLength % charPerLine == 0) lineCount else lineCount + 1
        }

        return lines
    }

    listOf(
        BubbleType.Bubble100,
        BubbleType.Bubble160,
        BubbleType.Bubble230,
        BubbleType.Bubble300,
    ).forEach { bubbleType ->
        val (textBoxWidth, _) = bubbleType.textBoxSize
        val fontSizeSp = bubbleType.fontStyle.fontSize.value
        val lineCount = calculateLineCount(text, textBoxWidth, fontSizeSp)

        when {
            text.length <= 5 && bubbleType == BubbleType.Bubble100 -> return BubbleType.Bubble100
            lineCount <= 2 && bubbleType == BubbleType.Bubble160 -> return BubbleType.Bubble160
            lineCount <= 3 && bubbleType == BubbleType.Bubble230 -> return BubbleType.Bubble230
            lineCount <= 4 && bubbleType == BubbleType.Bubble300 -> return BubbleType.Bubble300
        }
    }

    return BubbleType.BubbleMain
}

/**
 * 가장 바깥 원에 gradient가 적용된 원
 */
private fun DrawScope.drawOuterGradientCircle(center: Offset, radius: Float, colors: List<Color>) {
    val angleRadians = Math.toRadians(30.0)
    val startX = center.x - radius * cos(angleRadians).toFloat()
    val startY = center.y - radius * sin(angleRadians).toFloat()
    val endX = center.x + radius * cos(angleRadians).toFloat()
    val endY = center.y + radius * sin(angleRadians).toFloat()

    val gradient = Brush.linearGradient(
        colors = colors,
        start = Offset(startX, startY),
        end = Offset(endX, endY)
    )
    drawCircle(
        brush = gradient,
        radius = radius,
        center = center
    )
}

/**
 * 2번째 레이어에 해당하는 블러 효과가 적용된 원
 */
private fun DrawScope.drawLayerBlur(
    center: Offset,
    radius: Float,
    colors: List<Color>,
    blurRadius: Float
) {
    drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            this.asFrameworkPaint().apply {
                isAntiAlias = true
                when (colors.size) {
                    1 -> {
                        // 단일 색상
                        shader = android.graphics.LinearGradient(
                            center.x - radius,
                            center.y,
                            center.x + radius,
                            center.y,
                            intArrayOf(White000.toArgb(), colors[0].toArgb()),
                            floatArrayOf(0f, 1f),
                            android.graphics.Shader.TileMode.CLAMP
                        )
                    }

                    2 -> {
                        // 두 개의 색상 - 대각선 방향 Gradient
                        val angleRadians = Math.toRadians(30.0)
                        val startX = center.x - radius * cos(angleRadians).toFloat()
                        val startY = center.y - radius * sin(angleRadians).toFloat()
                        val endX = center.x + radius * cos(angleRadians).toFloat()
                        val endY = center.y + radius * sin(angleRadians).toFloat()
                        shader = android.graphics.LinearGradient(
                            startX, startY,
                            endX, endY,
                            intArrayOf(
                                White000.toArgb(),
                                colors[1].toArgb(),
                                colors[0].toArgb()
                            ),
                            floatArrayOf(0f, 0.46f, 1f),
                            android.graphics.Shader.TileMode.CLAMP
                        )
                    }

                    else -> {
                        // 기본 - 흰색 블러 처리만
                        color = android.graphics.Color.WHITE
                    }
                }
                // 블러 효과 추가
                maskFilter = android.graphics.BlurMaskFilter(
                    blurRadius,
                    android.graphics.BlurMaskFilter.Blur.NORMAL
                )
            }
        }
        canvas.drawCircle(center, radius, paint)
    }
}

/**
 * 라벨 3개 달리는 버블에서 내부 원들에 블러 처리된 원
 */
private fun DrawScope.drawCircleWithBlur(
    color: Color,
    center: Offset,
    radius: Float,
    blurRadius: Float
) {
    drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            this.color = color
            this.asFrameworkPaint().apply {
                isAntiAlias = true
                maskFilter = android.graphics.BlurMaskFilter(
                    blurRadius,
                    android.graphics.BlurMaskFilter.Blur.NORMAL
                )
            }
        }
        canvas.drawCircle(center, radius, paint)
    }
}

private fun DrawScope.drawOuterGradientBubbleDoor(
    center: Offset,
    width: Float,
    height: Float,
    colors: List<Color>
) {
    val combinedPath = Path().apply {
        // 직사각형 부분
        moveTo(center.x - width / 2, center.y) // 왼쪽 위
        lineTo(center.x - width / 2, center.y + height * 2f) // 왼쪽 아래
        lineTo(center.x + width / 2, center.y + height * 2f) // 오른쪽 아래
        lineTo(center.x + width / 2, center.y) // 오른쪽 위

        // 아치 부분
        cubicTo(
            x1 = center.x + width * 0.25f, y1 = center.y - height * 0.15f,
            x2 = center.x - width * 0.25f, y2 = center.y - height * 0.15f,
            x3 = center.x - width / 2, y3 = center.y
        )
        close()
    }

    val gradient = Brush.linearGradient(
        colors = colors,
        start = Offset(center.x - width / 2, center.y - height / 2),
        end = Offset(center.x + width / 2, center.y + height / 2),
    )

    drawPath(
        path = combinedPath,
        brush = gradient
    )
}

private fun DrawScope.drawBlurredOuterGradientBubbleDoor(
    center: Offset,
    width: Float,
    height: Float,
) {

    val blurredPath = Path().apply {
        // 직사각형 부분
        moveTo(center.x - width, center.y) // 왼쪽 위
        lineTo(center.x - width, center.y + height * 5f) // 왼쪽 아래
        lineTo(center.x + width, center.y + height * 5f) // 오른쪽 아래
        lineTo(center.x + width, center.y) // 오른쪽 위

        // 아치 부분
        cubicTo(
            x1 = center.x + width * 0.1f, y1 = center.y - height * 0.2f,
            x2 = center.x - width * 0.1f, y2 = center.y - height * 0.2f,
            x3 = center.x - width / 2, y3 = center.y
        )
        close()
    }

    drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            this.asFrameworkPaint().apply {
                isAntiAlias = true
                color = android.graphics.Color.WHITE
                this.alpha = 100 // 투명도 설정
                maskFilter = android.graphics.BlurMaskFilter(
                    30f,
                    android.graphics.BlurMaskFilter.Blur.NORMAL
                )
            }
        }
        canvas.drawPath(blurredPath, paint)
    }
}

object BubbleType {
    val BubbleMain = BubbleSize(
        size = 364.dp,
        innerSize = 326.dp,
        fontStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 24.sp
        ), // headingSmall
        textBoxSize = Pair(258, 144)
    )

    val Bubble300 = BubbleSize(
        size = 300.dp,
        innerSize = 270.dp,
        fontStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 24.sp
        ), // titleLarge
        textBoxSize = Pair(181, 96)
    )

    val Bubble230 = BubbleSize(
        size = 230.dp,
        innerSize = 206.dp,
        fontStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp
        ), // titleMedium
        textBoxSize = Pair(146, 57)
    )

    val Bubble160 = BubbleSize(
        size = 160.dp,
        innerSize = 144.dp,
        fontStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 18.sp
        ), // titleSmall
        textBoxSize = Pair(80, 36)
    )

    val Bubble100 = BubbleSize(
        size = 100.dp,
        innerSize = 90.dp,
        fontStyle = TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 18.sp
        ), // titleSmall
        textBoxSize = Pair(61, 17)
    )

    data class BubbleSize(
        val size: Dp,
        val innerSize: Dp,
        val fontStyle: TextStyle,
        val textBoxSize: Pair<Int, Int>,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewBubbleDoor() {
    EdisonTheme {
        BubbleDoor(
            bubble = BubbleModel(
                id = 0,
                title = "버블 제목",
                contentBlocks = listOf(
                    ContentBlockModel(ContentType.TEXT, "버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 버블 내용 ", 0),
                ),
                labels = listOf(
                    LabelModel(0, "라벨1", Aqua100, bubbles = listOf()),
                    LabelModel(1, "라벨2", Yellow100, bubbles = listOf()),
                    LabelModel(2, "라벨3", Red100, bubbles = listOf()),
                ),
                mainImage = null
            ),
            isEditable = true,
            onClick = { }
        )
    }
}