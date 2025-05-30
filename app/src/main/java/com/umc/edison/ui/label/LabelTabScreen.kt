package com.umc.edison.ui.label

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.AddLabelButton
import com.umc.edison.ui.components.BottomSheet
import com.umc.edison.ui.components.BottomSheetPopUp
import com.umc.edison.ui.components.LabelListItem
import com.umc.edison.ui.components.LabelModalContent
import com.umc.edison.ui.navigation.NavRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelTabScreen(
    navHostController: NavHostController,
    viewModel: LabelListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val baseState by viewModel.baseState.collectAsState()

    val draggedIndex = remember { mutableIntStateOf(-1) }

    LaunchedEffect(Unit) {
        viewModel.fetchLabels()
    }

    BaseContent(
        modifier = Modifier
            .clickable(
                onClick = {
                    draggedIndex.intValue = -1
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        baseState = baseState,
        clearToastMessage = { viewModel.clearToastMessage() },
    ) {
        if (uiState.labelEditMode == LabelEditMode.ADD || uiState.labelEditMode == LabelEditMode.EDIT) {
            BottomSheet(
                onDismiss = {
                    viewModel.updateEditMode(LabelEditMode.NONE)
                },
                baseState = baseState,
                clearToastMessage = { viewModel.clearToastMessage() }
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
                baseState = baseState,
                clearToastMessage = { viewModel.clearToastMessage() }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, top = 42.dp)
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
    onLabelClick: (String?) -> Unit,
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
                onClick = { onLabelClick(label.id) },
                onEditClick = { onEditClick(index) },
                onDeleteClick = { onDeleteClick(index) },
                onDrag = { onDrag(index) },
                resetDrag = resetDrag
            )
        }
    }
}
