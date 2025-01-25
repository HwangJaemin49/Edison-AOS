package com.umc.edison.ui.label

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.label.LabelDetailModel
import com.umc.edison.presentation.label.LabelDetailViewModel
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.ui.components.BubblePreview
import com.umc.edison.ui.components.calculateBubbleSize
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun LabelDetailScreen(
    navHostController: NavHostController,
    viewModel: LabelDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            LabelTopAppBar(
                label = uiState.label,
                navHostController = navHostController
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(White000)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else if (uiState.error != null) {
                Text(
                    text = "Error loading data",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                BubblesLayout(bubbles = uiState.label.bubbles)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelTopAppBar(
    label: LabelDetailModel,
    navHostController: NavHostController
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(color = label.labelColor, shape = CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "${label.labelName}  ${label.bubbles.size}",
                    style = MaterialTheme.typography.titleLarge,
                    color = Gray800
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_down),
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White000,
            titleContentColor = Gray800,
            navigationIconContentColor = Gray800
        )
    )
}

@Composable
fun BubblesLayout(
    bubbles: List<BubbleModel>,
    onBubbleClick: (BubbleModel) -> Unit = {}
) {
    val previousOffsets = mutableListOf<Dp>()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(bubbles.size) { index ->
            val bubble = bubbles[index]
            val bubbleSize = calculateBubbleSize(bubble)
            val xOffset = calculateBubbleXOffset(index, bubbleSize.size, previousOffsets)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = xOffset)
                    .size(bubbleSize.size + 4.dp)
            ) {
                BubblePreview(
                    bubble = bubble,
                    size = bubbleSize,
                    onClick = { onBubbleClick(bubble) }
                )
            }
        }
    }
}

/**
 * Calculate X-axis offset for a bubble based on its index and size.
 * Ensures no more than 2 consecutive increases or decreases.
 */
@Composable
private fun calculateBubbleXOffset(
    index: Int,
    bubbleSize: Dp,
    previousOffsets: MutableList<Dp>
): Dp {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val padding = 8.dp

    val maxXOffset = screenWidthDp - bubbleSize - padding

    if (index == 0) {
        val initialOffset = (padding.value.toInt()..maxXOffset.value.toInt()).random().dp
        previousOffsets.add(initialOffset)
        return initialOffset
    }

    var newOffset: Dp
    var attempts = 0

    do {
        newOffset = (padding.value.toInt()..maxXOffset.value.toInt()).random().dp
        val lastOffset = previousOffsets.last()
        val secondLastOffset =
            if (previousOffsets.size > 1) previousOffsets[previousOffsets.size - 2] else null

        val isIncreasing =
            secondLastOffset != null && newOffset > lastOffset && lastOffset > secondLastOffset
        val isDecreasing =
            secondLastOffset != null && newOffset < lastOffset && lastOffset < secondLastOffset

        attempts++

    } while ((isIncreasing || isDecreasing) && attempts < 10)

    previousOffsets.add(newOffset)
    return newOffset
}
