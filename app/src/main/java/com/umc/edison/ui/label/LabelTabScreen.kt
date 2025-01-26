package com.umc.edison.ui.label

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.presentation.label.LabelEditMode
import com.umc.edison.presentation.label.LabelListViewModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.components.AddLabelButton
import com.umc.edison.ui.components.BottomSheet
import com.umc.edison.ui.components.BottomSheetPopUp
import com.umc.edison.ui.components.LabelModalContent
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.White000

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelTabScreen(
    navHostController: NavHostController,
    viewModel: LabelListViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val uiState by viewModel.uiState.collectAsState()

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
        if (uiState.labelEditMode == LabelEditMode.ADD || uiState.labelEditMode == LabelEditMode.EDIT) {
            BottomSheet(
                onDismiss = {
                    viewModel.updateEditMode(LabelEditMode.NONE)
                },
                sheetState = sheetState,
            ) {
                LabelModalContent(
                    editMode = uiState.labelEditMode,
                    onDismiss = {
                        viewModel.updateEditMode(LabelEditMode.NONE)
                    },
                    onConfirm = { label ->
                        viewModel.confirmLabelModal(label)
                    },
                    label = uiState.selectedLabel
                )
            }
        }

        if (uiState.labelEditMode == LabelEditMode.DELETE) {
            BottomSheetPopUp(
                title = "${uiState.selectedLabel.name} 라벨을 삭제하시겠습니까?",
                cancelText = "취소",
                confirmText = "삭제",
                onDismiss = {
                    viewModel.updateEditMode(LabelEditMode.NONE)
                },
                onConfirm = {
                    viewModel.deleteSelectedLabel()
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
                    viewModel.updateEditMode(LabelEditMode.ADD)
                }
            )

            LabelList(
                labels = uiState.labels,
                draggedIndex = draggedIndex.intValue,
                onLabelClick = { labelId ->
                    navHostController.navigate(NavRoute.LabelDetail.createRoute(labelId))
                },
                onEditClick = { index ->
                    viewModel.updateEditMode(LabelEditMode.EDIT)
                    viewModel.updateSelectedLabel(uiState.labels[index])
                },
                onDeleteClick = { index ->
                    viewModel.updateEditMode(LabelEditMode.DELETE)
                    viewModel.updateSelectedLabel(uiState.labels[index])
                },
                onDrag = { index ->
                    // 드래그된 아이템 인덱스 업데이트
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
                count = label.bubbles.size,
                isDragged = index == draggedIndex,
                onClick = { onLabelClick(label.id) },
                onEditClick = { onEditClick(index) },
                onDeleteClick = { onDeleteClick(index) },
                onDrag = { onDrag(index) },
                resetDrag = resetDrag
            )
        }
    }
}
