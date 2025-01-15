package com.umc.edison.ui.space

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.umc.edison.R
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.presentation.space.LabelListViewModel
import com.umc.edison.ui.theme.EdisonTheme
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray600
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000
import com.umc.edison.ui.theme.Yellow100

@Composable
fun LabelListScreen(
    viewModel: LabelListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .background(White000)
                .padding(innerPadding)
                .padding(start = 24.dp, top = 42.dp)
                .fillMaxSize()
        ) {
            // 라벨 추가 버튼

            // 라벨 리스트
            LabelList(
                labels = uiState.labels,
                onLabelClick = { },
                onEditClick = { },
                onDeleteClick = { }
            )
        }
    }
}

@Composable
fun LabelList(
    labels: List<LabelModel>,
    onLabelClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    Column {
        labels.forEachIndexed { index, label ->
            LabelListItem(
                labelColor = label.color,
                labelText = label.name,
                count = label.bubbleCnt,
                onClick = { onLabelClick(index) },
                onEditClick = { onEditClick(index) },
                onDeleteClick = { onDeleteClick(index) }
            )
        }
    }
}

@Composable
fun LabelListItem(
    labelColor: Color,
    labelText: String,
    count: Int,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val offsetX = remember { mutableFloatStateOf(0f) }
    val visibilityThreshold = -60f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(White000)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .size(60.dp)
                .background(color = labelColor, shape = RoundedCornerShape(15.dp))
                .border(
                    width = 3.dp,
                    color = if (labelColor != White000) labelColor else Gray100,
                    shape = RoundedCornerShape(16.dp)
                )
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                if (offsetX.floatValue < visibilityThreshold) {
                                    offsetX.floatValue = -120f
                                } else {
                                    offsetX.floatValue = 0f
                                }
                            },
                            onHorizontalDrag = { change, dragAmount ->
                                change.consume()
                                offsetX.floatValue = (offsetX.floatValue + dragAmount).coerceIn(-120f, 0f)
                            }
                        )
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = labelText,
                    style = MaterialTheme.typography.titleLarge,
                    color = Gray800,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = count.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray600,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end = 16.dp)
                )

                AnimatedVisibility(
                    visible = offsetX.floatValue < visibilityThreshold,
                ) {
                    EditDeleteIcons(
                        onEditClick = onEditClick,
                        onDeleteClick = onDeleteClick
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray300)
            )
        }
    }
}

@Composable
fun EditDeleteIcons(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .width(120.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Gray800)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pencil),
                contentDescription = "Edit Label",
                modifier = Modifier
                    .size(28.dp)
                    .clickable(onClick = onEditClick),
                tint = Gray100
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color(0xFFFF0000))
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_trash),
                contentDescription = "Delete Label",
                modifier = Modifier
                    .size(28.dp)
                    .clickable(onClick = onDeleteClick),
                tint = Gray100
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLabelListItem() {
    EdisonTheme {
        LabelListItem(
            labelColor = Yellow100,
            labelText = "레이블 이름",
            count = 10,
            onClick = { },
            onEditClick = { },
            onDeleteClick = { }
        )
    }
}
