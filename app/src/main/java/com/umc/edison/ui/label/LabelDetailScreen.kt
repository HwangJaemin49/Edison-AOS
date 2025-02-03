package com.umc.edison.ui.label

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.presentation.label.LabelDetailMode
import com.umc.edison.presentation.label.LabelDetailViewModel
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BackButtonTopBar
import com.umc.edison.ui.components.BottomSheet
import com.umc.edison.ui.components.BottomSheetForDelete
import com.umc.edison.ui.components.BottomSheetPopUp
import com.umc.edison.ui.components.Bubble
import com.umc.edison.ui.components.BubblesLayout
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelDetailScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: LabelDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val isBlur = uiState.labelDetailMode != LabelDetailMode.NONE

    BackHandler(enabled = true) {
        if (uiState.labelDetailMode == LabelDetailMode.NONE) {
            navHostController.popBackStack()
        } else {
            resetEditMode(viewModel, updateShowBottomNav)
        }
    }

    Scaffold(
        bottomBar = {
            if (uiState.labelDetailMode == LabelDetailMode.EDIT) {
                BottomSheetForDelete(
                    selectedCnt = uiState.selectedBubbles.size,
                    showSelectedCnt = true,
                    onButtonClick = {
                        viewModel.getMovableLabels()
                        viewModel.updateEditMode(LabelDetailMode.MOVE)
                    },
                    onDelete = {
                        viewModel.updateEditMode(LabelDetailMode.DELETE)
                    },
                    buttonEnabled = uiState.selectedBubbles.isNotEmpty(),
                    buttonText = "버블 이동",
                )
            }
        }
    ) { innerPadding ->
        BaseContent(
            uiState = uiState,
            onDismiss = { viewModel.clearToastMessage() },
            modifier = Modifier.padding(innerPadding),
        ) {
            var onBubbleClick: (BubbleModel) -> Unit = {}
            var onBubbleLongClick: (BubbleModel) -> Unit = {}

            if (uiState.labelDetailMode == LabelDetailMode.EDIT) {
                onBubbleClick = { bubble ->
                    viewModel.toggleSelectBubble(bubble)
                }
            } else if (uiState.labelDetailMode == LabelDetailMode.NONE) {
                onBubbleClick = { bubble ->
                    viewModel.selectBubble(bubble)
                    viewModel.updateEditMode(LabelDetailMode.VIEW)
                }
                onBubbleLongClick = { bubble ->
                    viewModel.selectBubble(bubble)
                    viewModel.updateEditMode(LabelDetailMode.EDIT)
                    updateShowBottomNav(false)
                }
            }

            Column(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if (uiState.labelDetailMode == LabelDetailMode.EDIT) {
                        resetEditMode(viewModel, updateShowBottomNav)
                    }
                }
            ) {
                LabelTopAppBar(
                    label = uiState.label,
                    navHostController = navHostController,
                    viewModel = viewModel,
                    updateShowBottomNav = updateShowBottomNav
                )

                BubblesLayout(
                    bubbles = uiState.label.bubbles,
                    onBubbleClick = onBubbleClick,
                    onBubbleLongClick = onBubbleLongClick,
                    isBlur = isBlur,
                    selectedBubble = uiState.selectedBubbles,
                )
            }

            if (uiState.labelDetailMode == LabelDetailMode.VIEW && uiState.selectedBubbles.isNotEmpty()) {
                val bubble = uiState.selectedBubbles.first()
                Bubble(
                    bubble = bubble,
                    onBackScreenClick = {
                        viewModel.updateEditMode(LabelDetailMode.NONE)
                    },
                    onBubbleClick = {
                        // TODO: 버블 클릭 시 동작 추가
                    }
                )
            } else if (uiState.labelDetailMode == LabelDetailMode.MOVE) {
                BottomSheet(
                    onDismiss = {
                        viewModel.updateEditMode(LabelDetailMode.EDIT)
                    },
                ) {
                    LabelMoveModalContent(
                        labels = uiState.movableLabels,
                        onDismiss = {
                            viewModel.updateEditMode(LabelDetailMode.EDIT)
                        },
                        onConfirm = { label ->
                            viewModel.moveSelectedBubbles(label, showBottomNav = updateShowBottomNav)
                        }
                    )
                }

            } else if (uiState.labelDetailMode == LabelDetailMode.DELETE) {
                BottomSheetPopUp(
                    title = "${uiState.selectedBubbles.size} 개의 버블을 삭제하시겠습니까?",
                    cancelText = "취소",
                    confirmText = "삭제",
                    onDismiss = {
                        viewModel.updateEditMode(LabelDetailMode.EDIT)
                    },
                    onConfirm = {
                        viewModel.deleteSelectedBubbles(showBottomNav = updateShowBottomNav)
                    },
                )
            }
        }
    }
}

private fun resetEditMode(
    viewModel: LabelDetailViewModel,
    updateShowBottomNav: (Boolean) -> Unit
) {
    viewModel.updateEditMode(LabelDetailMode.NONE)
    updateShowBottomNav(true)
}

@Composable
fun LabelTopAppBar(
    label: LabelModel,
    navHostController: NavHostController,
    viewModel: LabelDetailViewModel,
    updateShowBottomNav: (Boolean) -> Unit
) {
    BackButtonTopBar(
        onBack = {
            resetEditMode(viewModel, updateShowBottomNav)
            navHostController.popBackStack()
        },
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = label.color, shape = CircleShape)
                .border(
                    width = 3.dp,
                    color = if (label.color != White000) label.color else Gray100,
                    shape = RoundedCornerShape(16.dp)
                )
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "${label.name}  ${label.bubbles.size}",
            style = MaterialTheme.typography.titleLarge,
            color = Gray800
        )
    }
}
