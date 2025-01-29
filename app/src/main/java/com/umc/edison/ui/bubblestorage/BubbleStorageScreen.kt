import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.umc.edison.ui.components.Bubble
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.ContentBlockModel
import com.umc.edison.presentation.storage.BubbleStorageViewModel
import com.umc.edison.ui.components.BubblePreview
import com.umc.edison.ui.components.calculateBubbleSize
import com.umc.edison.ui.theme.White000
import kotlin.random.Random

@Composable
fun BubbleStorageScreen(viewModel: BubbleStorageViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    val (selectedBubble, setSelectedBubble) = remember { mutableStateOf<BubbleModel?>(null) }
    val (longClickedBubble, setLongClickedBubble) = remember { mutableStateOf<BubbleModel?>(null) }
    val highlightedBubbles = remember { mutableStateListOf<BubbleModel>() } // 강조된 버블 리스트

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            uiState.error != null -> {
                Text(
                    text = "Error loading data",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                BubblesLayout(
                    bubbles = uiState.bubbles,
                    onBubbleClick = { bubble ->
                        if (longClickedBubble != null) {
                            // 강조 리스트에 추가
                            if (!highlightedBubbles.contains(bubble)) {
                                highlightedBubbles.add(bubble)
                            }
                        } else {
                            setSelectedBubble(bubble) // 일반 클릭 시 동작
                        }
                    },
                    onBubbleLongClick = { bubble ->
                        setLongClickedBubble(bubble) // 길게 클릭 시 해당 버블 설정
                        highlightedBubbles.clear() // 강조된 버블 초기화
                    },
                    isBlur = longClickedBubble != null, // 블러 처리 여부
                    selectedBubble = highlightedBubbles // 강조된 버블 리스트
                )
            }
        }

        // 선택된 Bubble 컴포넌트 표시
        if (selectedBubble != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { setSelectedBubble(null) } // 클릭 시 닫기
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Bubble(
                    bubble = selectedBubble,
                    onClick = { setSelectedBubble(null) }
                )
            }
        }
    }
}


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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = xOffset)
                    .size(calculateBubbleSize(bubble).size + 4.dp)
            ) {
                BubblePreview(
                    bubble = bubble,
                    size = calculateBubbleSize(bubble),
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

    val maxXOffset = screenWidthDp - calculateBubbleSize(bubble).size - padding

    // 랜덤 초기 오프셋 계산
    return (padding.value.toInt()..maxXOffset.value.toInt()).random().dp
}


@Preview(showBackground = true)
@Composable
fun BubbleStorageScreenPreview() {
    BubbleStorageScreen()
}