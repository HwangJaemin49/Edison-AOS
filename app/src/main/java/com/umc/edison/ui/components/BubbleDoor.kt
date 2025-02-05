package com.umc.edison.ui.components

import android.graphics.BlurMaskFilter
import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import coil3.size.Size
import com.umc.edison.domain.model.ContentType
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.ContentBlockModel
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray700
import com.umc.edison.ui.theme.Gray800
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichText
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.umc.edison.presentation.edison.BubbleInputState
import com.umc.edison.presentation.edison.parseHtml
import com.umc.edison.ui.theme.White000
import com.umc.edison.ui.theme.Yellow100

@Composable
fun BubbleDoor(
    bubble: BubbleModel,
    onClick: (() -> Unit)? = null,
    isEditable: Boolean = false,
    onBubbleUpdate: (BubbleModel) -> Unit = {},
    onImageDeleted: (ContentBlockModel) -> Unit = {},
    bubbleInputState: BubbleInputState = BubbleInputState.DEFAULT,
    linkClicked: (Int) -> Unit = {},
) {
    val colors = bubble.labels.map { it.color }
    val outerColors = when (colors.size) {
        0 -> emptyList()
        1 -> listOf(colors[0])
        2 -> listOf(colors[0], colors[1])
        3 -> listOf(colors[0], colors[1], colors[2])
        else -> colors
    }
    val innerColors = when (colors.size) {
        0 -> listOf(White000)
        1 -> listOf(White000, colors[0])
        2 -> listOf(White000, colors[0], colors[1])
        3 -> listOf(White000, colors[0], colors[1], colors[2])
        else -> colors
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
            .clickable(
                onClick = onClick ?: {},
                enabled = onClick != null
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        // 배경 Canvas (전체 높이 채우기)
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawOuterGradientBubbleDoor(
                width = size.width,
                height = size.height,
                colors = outerColors
            )

            drawBlurredInnerGradientBubbleDoor(
                width = size.width,
                height = size.height,
                colors = innerColors
            )
        }

        // 콘텐츠 영역
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 24.dp, top = 80.dp, end = 24.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isEditable) {
                BubbleContent(
                    bubble = bubble,
                    onBubbleChange = onBubbleUpdate,
                    uiState = bubbleInputState,
                    deleteClicked = onImageDeleted,
                )
            } else {
                BubbleContent(
                    bubble = bubble,
                    linkClicked = linkClicked
                )
            }
        }
    }
}

@OptIn(ExperimentalRichTextApi::class, ExperimentalLayoutApi::class)
@Composable
private fun BubbleContent(
    bubble: BubbleModel,
    linkClicked: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Title
        if (bubble.title != null) {
            Text(
                text = bubble.title,
                style = MaterialTheme.typography.displayMedium,
                color = Gray800,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(4.dp))
        }

        // Content Blocks
        bubble.contentBlocks.forEach { contentBlock ->
            when (contentBlock.type) {
                ContentType.TEXT -> {
                    val richTextState = rememberRichTextState()

                    LaunchedEffect(contentBlock.content) {
                        richTextState.setHtml(contentBlock.content)
                    }

                    RichText(
                        state = richTextState,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray800,
                        modifier = Modifier.fillMaxWidth()
                    )
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

        FlowRow (
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            bubble.backLinks.forEach { backLink ->

                val myUriHandler by remember {
                    mutableStateOf(object : UriHandler {
                        override fun openUri(uri: String) {
                            val bubbleId = uri.toIntOrNull()
                            if (bubbleId != null) {
                                linkClicked(bubbleId)
                            }
                        }
                    })
                }

                val richTextState = rememberRichTextState()
                val isInitialized = remember(backLink.id) { mutableStateOf(false) }

                if (!isInitialized.value) {
                    val selectedTitle = backLink.title?.takeIf { it.isNotBlank() }
                        ?: backLink.contentBlocks
                            .filter { it.type == ContentType.TEXT }
                            .firstOrNull { it.content.parseHtml().isNotBlank() }
                            ?.content?.parseHtml()?.take(5)
                        ?: "내용 없음"

                    val splitTitle = selectedTitle.split("\n")

                    richTextState.addLink(
                        text = "[[${splitTitle[0]}]]",
                        url = "${backLink.id}"
                    )

                    isInitialized.value = true
                }

                CompositionLocalProvider(LocalUriHandler provides myUriHandler){
                    RichText(
                        state = richTextState,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray800,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        bubble.linkedBubble?.let { linkedBubble ->
            val myUriHandler by remember {
                mutableStateOf(object : UriHandler {
                    override fun openUri(uri: String) {
                        val bubbleId = uri.toIntOrNull()
                        if (bubbleId != null) {
                            linkClicked(bubbleId)
                        }
                    }
                })
            }

            val richTextState = rememberRichTextState()
            val isInitialized = remember(linkedBubble.id) { mutableStateOf(false) }

            if (!isInitialized.value) {
                val selectedTitle = linkedBubble.title?.takeIf { it.isNotBlank() }
                    ?: linkedBubble.contentBlocks
                        .filter { it.type == ContentType.TEXT }
                        .firstOrNull { it.content.parseHtml().isNotBlank() }
                        ?.content?.parseHtml()?.take(5)
                    ?: "내용 없음"

                val splitTitle = selectedTitle.split("\n")

                richTextState.addLink(
                    text = "[[${splitTitle[0]}]]",
                    url = "${linkedBubble.id}"
                )

                isInitialized.value = true
            }

            CompositionLocalProvider(LocalUriHandler provides myUriHandler){
                RichText(
                    state = richTextState,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray800,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalRichTextApi::class, ExperimentalLayoutApi::class)
@Composable
private fun BubbleContent(
    bubble: BubbleModel,
    onBubbleChange: (BubbleModel) -> Unit,
    uiState: BubbleInputState,
    deleteClicked: (ContentBlockModel) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        BasicTextField(
            value = bubble.title ?: "",
            onValueChange = { newTitle ->
                onBubbleChange(
                    bubble.copy(title = newTitle)
                )
            },
            textStyle = MaterialTheme.typography.displayMedium.copy(color = Gray800),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (bubble.title.isNullOrEmpty()) {
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

        var deletedImageBlockId by remember { mutableStateOf<Int?>(null) }

        bubble.contentBlocks.forEachIndexed { _, contentBlock ->
            when (contentBlock.type) {
                ContentType.TEXT -> {
                    val richTextState = rememberSaveable(
                        key = "richTextState_${contentBlock.position}",
                        saver = RichTextState.Saver
                    ) {
                        RichTextState().apply {
                            setHtml(contentBlock.content)
                        }
                    }

                    val isInitialized = remember(contentBlock.position) { mutableStateOf(false) }

                    LaunchedEffect(deletedImageBlockId) {
                        deletedImageBlockId?.let {
                            richTextState.setHtml(contentBlock.content)
                            deletedImageBlockId = null
                        }
                    }

                    SideEffect {
                        if (!isInitialized.value) {
                            richTextState.setHtml(contentBlock.content)
                            isInitialized.value = true
                        }
                    }

                    LaunchedEffect(richTextState.toHtml()) {
                        onBubbleChange(
                            bubble.copy(
                                contentBlocks = bubble.contentBlocks.map {
                                    if (it.position == contentBlock.position) {
                                        it.copy(content = richTextState.toHtml())
                                    } else {
                                        it
                                    }
                                }
                            )
                        )
                    }

                    if (uiState.selectedTextStyles.contains(TextStyle.BOLD)) {
                        richTextState.addSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    } else {
                        richTextState.removeSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    }

                    if (uiState.selectedTextStyles.contains(TextStyle.ITALIC)) {
                        richTextState.addSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                    } else {
                        richTextState.removeSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                    }

                    if (uiState.selectedTextStyles.contains(TextStyle.UNDERLINE)) {
                        richTextState.addSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                    } else {
                        richTextState.removeSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                    }

                    if (uiState.selectedTextStyles.contains(TextStyle.HIGHLIGHT)) {
                        richTextState.addSpanStyle(SpanStyle(background = Yellow100))
                    } else {
                        richTextState.removeSpanStyle(SpanStyle(background = Yellow100))
                    }

                    if (uiState.selectedListStyle == ListStyle.UNORDERED) {
                        richTextState.addUnorderedList()
                    } else {
                        richTextState.removeUnorderedList()
                    }

                    if (uiState.selectedListStyle == ListStyle.ORDERED) {
                        richTextState.addOrderedList()
                    } else {
                        richTextState.removeOrderedList()
                    }

                    BasicRichTextEditor(
                        state = richTextState,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = Gray800),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (richTextState.toHtml() == "<br>" && bubble.contentBlocks.size == 1) {
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
                }

                ContentType.IMAGE -> {
                    val aspectRatio = calculateAspectRatio(contentBlock.content)
                    var showDeleteButton by remember { mutableStateOf(false) }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(aspectRatio)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        showDeleteButton = true
                                    }
                                )
                            }
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(contentBlock.content)
                                    .crossfade(true)
                                    .size(Size.ORIGINAL)
                                    .build()
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp))
                        )

                        if (showDeleteButton) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { showDeleteButton = false },
                                contentAlignment = Alignment.Center
                            ) {
                                Button(

                                    shape = RoundedCornerShape(100.dp),
                                    onClick = {
                                        println(bubble)
                                        showDeleteButton = false
                                        deleteClicked(contentBlock)
                                        println(bubble)
                                        deletedImageBlockId = contentBlock.position
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Gray700,
                                    )
                                ) {
                                    Text(
                                        text = "삭제하기",
                                        style = MaterialTheme.typography.bodyMedium.copy(color = Gray100),
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        FlowRow (
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            bubble.backLinks.forEach { backLink ->

                val richTextState = rememberRichTextState()
                val isInitialized = remember(backLink.id) { mutableStateOf(false) }

                if (!isInitialized.value) {
                    val selectedTitle = backLink.title?.takeIf { it.isNotBlank() }
                        ?: backLink.contentBlocks
                            .filter { it.type == ContentType.TEXT }
                            .firstOrNull { it.content.parseHtml().isNotBlank() }
                            ?.content?.parseHtml()?.take(5)
                        ?: "내용 없음"

                    val splitTitle = selectedTitle.split("\n")

                    richTextState.addLink(
                        text = "[[${splitTitle[0]}]]",
                        url = "${backLink.id}"
                    )

                    isInitialized.value = true
                }

                BasicRichText(
                    state = richTextState,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Gray800),
                )
            }
        }

        bubble.linkedBubble?.let { linkedBubble ->
            val richTextState = rememberRichTextState()
            val isInitialized = remember(linkedBubble.id) { mutableStateOf(false) }

            if (!isInitialized.value) {
                val selectedTitle = linkedBubble.title?.takeIf { it.isNotBlank() }
                    ?: linkedBubble.contentBlocks
                        .filter { it.type == ContentType.TEXT }
                        .firstOrNull { it.content.parseHtml().isNotBlank() }
                        ?.content?.parseHtml()?.take(5)
                    ?: "내용 없음"

                val splitTitle = selectedTitle.split("\n")

                richTextState.addLink(
                    text = "[[${splitTitle[0]}]]",
                    url = "${linkedBubble.id}"
                )

                isInitialized.value = true
            }

            BasicRichText(
                state = richTextState,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium.copy(color = Gray800),
            )
        }
    }
}

@Composable
private fun calculateAspectRatio(content: String): Float {
    var aspectRatio by remember { mutableFloatStateOf(1f) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(content)
            .crossfade(true)
            .size(Size.ORIGINAL)
            .build(),
        onSuccess = { result ->
            val width = result.painter.intrinsicSize.width
            val height = result.painter.intrinsicSize.height
            if (width > 0 && height > 0) {
                aspectRatio = width / height
            }
        }
    )

    return aspectRatio
}

private fun DrawScope.drawOuterGradientBubbleDoor(
    width: Float,
    height: Float,
    colors: List<Color>
) {
    val circleCenter = Offset(x = width / 2, y = width)
    val rectTop = circleCenter.y - (width * 0.5f)

    val path = Path().apply {
        addOval(Rect(center = circleCenter, radius = width))
        addRect(Rect(Offset(0f, rectTop), androidx.compose.ui.geometry.Size(width, height)))
    }

    drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            this.asFrameworkPaint().apply {
                isAntiAlias = true
                when (colors.size) {
                    0 -> color = Gray100.toArgb()
                    1 -> color = colors[0].copy(alpha = 0.5f).toArgb()
                    2 -> {
                        val startX = circleCenter.x - width
                        val endX = circleCenter.x + width
                        val startY = rectTop - height / 2
                        val endY = rectTop - height / 2
                        shader = LinearGradient(
                            startX, startY,
                            endX, endY,
                            intArrayOf(
                                colors[0].copy(alpha = 0.5f).toArgb(),
                                colors[1].copy(alpha = 0.5f).toArgb()
                            ),
                            floatArrayOf(0.0f, 1.0f),
                            Shader.TileMode.CLAMP
                        )
                    }

                    3 -> {
                        val startX = circleCenter.x - width
                        val endX = circleCenter.x + width
                        val startY = rectTop - height / 2
                        val endY = rectTop - height / 2
                        shader = LinearGradient(
                            startX, startY,
                            endX, endY,
                            intArrayOf(
                                colors[0].copy(alpha = 0.5f).toArgb(),
                                colors[1].copy(alpha = 0.5f).toArgb(),
                                colors[2].copy(alpha = 0.5f).toArgb()
                            ),
                            floatArrayOf(0.0f, 0.52f, 1.0f),
                            Shader.TileMode.CLAMP
                        )
                    }

                    else -> color = Gray800.toArgb()
                }
            }
        }
        canvas.clipPath(path)
        canvas.drawPath(path, paint)
    }
}

private fun DrawScope.drawBlurredInnerGradientBubbleDoor(
    width: Float,
    height: Float,
    colors: List<Color>
) {
    val circleCenter = Offset(x = width / 2, y = width * 1.04f)
    val rectTop = circleCenter.y - (width * 0.5f)

    val path = Path().apply {
        addOval(Rect(center = circleCenter, radius = width))
        addRect(Rect(Offset(0f, rectTop), androidx.compose.ui.geometry.Size(width, height)))
    }

    drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            this.asFrameworkPaint().apply {
                isAntiAlias = true
                when (colors.size) {
                    1 -> {
                        val startX = circleCenter.x
                        val endX = circleCenter.x
                        val endY = rectTop + height
                        shader = LinearGradient(
                            startX, rectTop,
                            endX, endY,
                            intArrayOf(
                                colors[0].copy(alpha = 0.5f).toArgb(),
                                Gray100.copy(alpha = 0.5f).toArgb()
                            ),
                            floatArrayOf(0.0f, 1.0f),
                            Shader.TileMode.CLAMP
                        )
                    }

                    2 -> {
                        val startX = circleCenter.x
                        val endX = circleCenter.x
                        val endY = rectTop + height
                        shader = LinearGradient(
                            startX, rectTop,
                            endX, endY,
                            intArrayOf(
                                colors[0].copy(alpha = 0.5f).toArgb(),
                                Color(0xFFF7F9FB).copy(alpha = 0.5f).toArgb(),
                                colors[1].copy(alpha = 0.5f).toArgb(),
                            ),
                            floatArrayOf(0.0f, 0.54f, 1.0f),
                            Shader.TileMode.CLAMP
                        )
                    }

                    3 -> {
                        val startX = circleCenter.x - width
                        val endX = circleCenter.x + width
                        val endY = rectTop + height
                        shader = LinearGradient(
                            startX, rectTop,
                            endX, endY,
                            intArrayOf(
                                colors[0].copy(alpha = 0.5f).toArgb(),
                                colors[1].copy(alpha = 0.5f).toArgb(),
                                colors[2].copy(alpha = 0.5f).toArgb()
                            ),
                            floatArrayOf(0.0f, 0.49f, 1.0f),
                            Shader.TileMode.CLAMP
                        )
                    }

                    4 -> {
                        val startX = circleCenter.x - width
                        val endX = circleCenter.x + width
                        val endY = rectTop + height
                        shader = LinearGradient(
                            startX, rectTop,
                            endX, endY,
                            intArrayOf(
                                colors[0].copy(alpha = 0.5f).toArgb(),
                                colors[1].copy(alpha = 0.5f).toArgb(),
                                colors[2].copy(alpha = 0.5f).toArgb(),
                                colors[3].copy(alpha = 0.5f).toArgb()
                            ),
                            floatArrayOf(0.0f, 0.37f, 0.66f, 1.0f),
                            Shader.TileMode.CLAMP
                        )
                    }

                    else -> color = Gray800.toArgb()
                }
                maskFilter = BlurMaskFilter(80f, BlurMaskFilter.Blur.NORMAL)
            }
        }
        canvas.clipPath(path)
        canvas.drawPath(path, paint)
    }
}
