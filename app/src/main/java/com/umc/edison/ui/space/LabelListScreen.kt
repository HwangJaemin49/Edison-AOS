package com.umc.edison.ui.space

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.umc.edison.R
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.presentation.space.LabelListViewModel
import com.umc.edison.ui.components.BottomSheet
import com.umc.edison.ui.components.BottomSheetPopUp
import com.umc.edison.ui.theme.EdisonTheme
import com.umc.edison.ui.theme.Gray200
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.Red100
import com.umc.edison.ui.theme.White000

enum class EditMode {
    NONE, ADD, EDIT, DELETE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelListScreen(
    viewModel: LabelListViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val uiState by viewModel.uiState.collectAsState()
    val labelState = remember { mutableStateOf(LabelModel(name = "", color = Red100)) }

    val draggedIndex = remember { mutableIntStateOf(-1) }

    Scaffold(
        modifier = Modifier
            .clickable(
                onClick = {
                    draggedIndex.intValue = -1
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ) { innerPadding ->
        if (uiState.editMode == EditMode.ADD || uiState.editMode == EditMode.EDIT) {
            BottomSheet(
                onDismiss = {
                    viewModel.updateEditMode(EditMode.NONE)
                },
                sheetState = sheetState,
            ) {
                LabelModalContent(
                    editMode = uiState.editMode,
                    onDismiss = {
                        viewModel.updateEditMode(EditMode.NONE)
                    },
                    onConfirm = { label ->
                        labelState.value = label
                        viewModel.confirmLabelModal(labelState.value)
                    },
                    label = labelState.value
                )
            }
        }

        if (uiState.editMode == EditMode.DELETE) {
            BottomSheetPopUp(
                title = "${labelState.value.name} 라벨을 삭제하시겠습니까?",
                cancelText = "취소",
                confirmText = "삭제",
                onDismiss = {
                    viewModel.updateEditMode(EditMode.NONE)
                },
                onConfirm = {
                    viewModel.deleteLabel(labelState.value)
                },
                sheetState = sheetState,
            )
        }

        Column(
            modifier = Modifier
                .background(White000)
                .padding(innerPadding)
                .padding(start = 24.dp, top = 42.dp)
                .fillMaxSize()
        ) {
            AddLabelButton(
                onClick = {
                    labelState.value = LabelModel(name = "", color = Gray300)
                    viewModel.updateEditMode(EditMode.ADD)
                }
            )

            LabelList(
                labels = uiState.labels,
                draggedIndex = draggedIndex.intValue,
                onLabelClick = { },
                onEditClick = { index ->
                    labelState.value = uiState.labels[index]
                    viewModel.updateEditMode(EditMode.EDIT)
                },
                onDeleteClick = { index ->
                    labelState.value = uiState.labels[index]
                    viewModel.updateEditMode(EditMode.DELETE)
                },
                onDrag = { index ->
                    draggedIndex.intValue = index
                },
                resetDrag = {
                    draggedIndex.intValue = -1
                }
            )
        }
    }
}

@Composable
fun LabelList(
    labels: List<LabelModel>,
    draggedIndex: Int,
    onLabelClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onDrag: (Int) -> Unit,
    resetDrag: () -> Unit
) {
    Column {
        labels.forEachIndexed { index, label ->
            LabelListItem(
                labelColor = label.color,
                labelText = label.name,
                count = label.bubbleCnt,
                isDragged = index == draggedIndex,
                onClick = { onLabelClick(index) },
                onEditClick = { onEditClick(index) },
                onDeleteClick = { onDeleteClick(index) },
                onDrag = { onDrag(index) },
                resetDrag = resetDrag
            )
        }
    }
}

@Composable
fun AddLabelButton(
    onClick: () -> Unit
) {
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
                .background(color = Gray200, shape = RoundedCornerShape(15.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "Add Label",
                modifier = Modifier
                    .size(42.dp),
                tint = Gray500
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "새로운 라벨 추가하기",
                    style = MaterialTheme.typography.titleLarge,
                    color = Gray800,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_right),
                    contentDescription = "Add Label",
                    modifier = Modifier
                        .size(36.dp)
                        .padding(end = 16.dp),
                    tint = Gray500
                )
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

@Preview(showBackground = true)
@Composable
fun PreviewAddLabelButton() {
    EdisonTheme {
        AddLabelButton(
            onClick = { },
        )
    }
}
