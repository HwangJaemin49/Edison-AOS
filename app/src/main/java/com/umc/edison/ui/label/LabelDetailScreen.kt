package com.umc.edison.ui.label

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.presentation.baseBubble.LabelDetailMode
import com.umc.edison.presentation.label.LabelDetailViewModel
import com.umc.edison.ui.components.Bubble
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BottomSheet
import com.umc.edison.ui.components.BottomSheetForDelete
import com.umc.edison.ui.components.BottomSheetPopUp
import com.umc.edison.ui.components.BubbleType
import com.umc.edison.ui.components.BubblesLayout
import com.umc.edison.ui.components.LabelTagList
import com.umc.edison.ui.components.LabelTopAppBar
import com.umc.edison.ui.components.calculateBubbleSize
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray800

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelDetailScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: LabelDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        updateShowBottomNav(true)
        viewModel.updateEditMode(LabelDetailMode.NONE)
    }

    BackHandler(enabled = true) {
        if (uiState.mode == LabelDetailMode.NONE) {
            navHostController.popBackStack()
        } else {
            viewModel.updateEditMode(LabelDetailMode.NONE)
            updateShowBottomNav(true)
        }
    }

    BaseContent(
        uiState = uiState,
        clearToastMessage = { viewModel.clearToastMessage() },
        bottomBar = {
            if (uiState.mode == LabelDetailMode.EDIT) {
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
        },
    ) {
        var onBubbleClick: (BubbleModel) -> Unit = {}
        var onBubbleLongClick: (BubbleModel) -> Unit = {}

        if (uiState.mode == LabelDetailMode.EDIT) {
            onBubbleClick = { bubble ->
                viewModel.toggleSelectBubble(bubble)
            }
        } else if (uiState.mode == LabelDetailMode.NONE) {
            onBubbleClick = { bubble ->
                viewModel.selectBubble(bubble)
                viewModel.updateEditMode(LabelDetailMode.VIEW)
                val bubbleSize = calculateBubbleSize(bubble)

                if (bubbleSize == BubbleType.BubbleDoor) {
                    updateShowBottomNav(false)
                }
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
                if (uiState.mode == LabelDetailMode.EDIT) {
                    updateShowBottomNav(true)
                }
            }
        ) {
            LabelTopAppBar(
                label = uiState.label,
                onBackClick = {
                    viewModel.updateEditMode(LabelDetailMode.NONE)
                    updateShowBottomNav(true)
                    navHostController.popBackStack()
                }
            )

            BubblesLayout(
                bubbles = uiState.label.bubbles,
                onBubbleClick = onBubbleClick,
                onBubbleLongClick = onBubbleLongClick,
                isBlur = uiState.mode != LabelDetailMode.NONE,
                selectedBubble = uiState.selectedBubbles
            )
        }

        if (uiState.mode == LabelDetailMode.VIEW && uiState.selectedBubbles.isNotEmpty()) {
            val bubble = uiState.selectedBubbles.first()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Gray800.copy(alpha = 0.5f))
                    .clickable(onClick = {
                        viewModel.updateEditMode(LabelDetailMode.NONE)
                        updateShowBottomNav(true)
                    })
                    .padding(top = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Bubble(
                    bubble = bubble,
                    onBubbleClick = {
                        navHostController.navigate(NavRoute.BubbleEdit.createRoute(bubble.id))
                    },
                    onLinkedBubbleClick = { linkedBubbleId ->
                        navHostController.navigate(NavRoute.BubbleEdit.createRoute(linkedBubbleId))
                    }
                )

                LabelTagList(
                    labels = bubble.labels,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
        } else if (uiState.mode == LabelDetailMode.MOVE) {
            BottomSheet(
                uiState = uiState,
                clearToastMessage = { viewModel.clearToastMessage() },
                onDismiss = {
                    viewModel.updateEditMode(LabelDetailMode.EDIT)
                },
            ) {
                LabelSelectModalContent(
                    labels = uiState.movableLabels,
                    onDismiss = {
                        viewModel.updateEditMode(LabelDetailMode.EDIT)
                    },
                    onConfirm = { labelList ->
                        viewModel.moveSelectedBubbles(labelList.first(), showBottomNav = updateShowBottomNav)
                    },
                )
            }
        } else if (uiState.mode == LabelDetailMode.DELETE) {
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
                uiState = uiState,
                clearToastMessage = { viewModel.clearToastMessage() }
            )
        }
    }
}
