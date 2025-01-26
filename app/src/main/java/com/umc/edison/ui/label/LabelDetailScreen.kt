package com.umc.edison.ui.label

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.label.BubbleEditMode
import com.umc.edison.presentation.label.LabelDetailViewModel
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.components.BottomSheet
import com.umc.edison.ui.components.BottomSheetForDelete
import com.umc.edison.ui.components.BottomSheetPopUp
import com.umc.edison.ui.components.Bubble
import com.umc.edison.ui.components.BubblesLayout
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelDetailScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: LabelDetailViewModel = hiltViewModel(),
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val uiState by viewModel.uiState.collectAsState()
    val isBlur = uiState.bubbleEditMode == BubbleEditMode.EDIT

    Scaffold(
        bottomBar = {
            if (uiState.bubbleEditMode == BubbleEditMode.EDIT) {
                BottomSheetForDelete(
                    selectedCnt = uiState.selectedBubbles.size,
                    showSelectedCnt = true,
                    onButtonClick = {
                        viewModel.getMovableLabels()
                    },
                    onDelete = {
                        viewModel.updateEditMode(BubbleEditMode.DELETE)
                    },
                    buttonEnabled = uiState.selectedBubbles.isNotEmpty(),
                    buttonText = "버블 이동",
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(White000)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (uiState.error != null) {
                Text(
                    text = "Error loading data",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                val onBubbleClick: (BubbleModel) -> Unit
                val onBubbleLongClick: (BubbleModel) -> Unit

                if (uiState.bubbleEditMode == BubbleEditMode.EDIT) {
                    onBubbleClick = { bubble ->
                        viewModel.toggleSelectBubble(bubble)
                    }
                    onBubbleLongClick = {}
                } else {
                    onBubbleClick = { bubble ->
                        viewModel.selectBubble(bubble)
                        viewModel.updateEditMode(BubbleEditMode.VIEW)
                    }
                    onBubbleLongClick = { bubble ->
                        viewModel.selectBubble(bubble)
                        viewModel.updateEditMode(BubbleEditMode.EDIT)
                        updateShowBottomNav(false)
                    }
                }

                Column(
                    modifier = Modifier.clickable(
                        indication = null, // Ripple 효과 제거
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        if (uiState.bubbleEditMode == BubbleEditMode.EDIT) {
                            resetEditMode(viewModel, updateShowBottomNav)
                        }
                    }
                ) {
                    LabelTopAppBar(
                        label = uiState.label,
                        navHostController = navHostController
                    )

                    BubblesLayout(
                        bubbles = uiState.label.bubbles,
                        onBubbleClick = onBubbleClick,
                        onBubbleLongClick = onBubbleLongClick,
                        isBlur = isBlur,
                        selectedBubble = uiState.selectedBubbles,
                    )
                }

                if (uiState.bubbleEditMode == BubbleEditMode.VIEW && uiState.selectedBubbles.isNotEmpty()) {
                    val bubble = uiState.selectedBubbles.first()
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clickable(onClick = {
                                viewModel.updateEditMode(BubbleEditMode.NONE)
                            }),
                        contentAlignment = Alignment.Center
                    ) {
                        Bubble(
                            bubble = bubble,
                            onClick = {
                                navHostController.navigate(NavRoute.BubbleEdit.createRoute(bubble.id))
                            }
                        )
                    }
                } else if (uiState.bubbleEditMode == BubbleEditMode.MOVE) {
                    BottomSheet(
                        onDismiss = {
                            viewModel.updateEditMode(BubbleEditMode.EDIT)
                        },
                        sheetState = sheetState,
                    ) {
                        LabelMoveModalContent(
                            labels = uiState.movableLabels,
                            onDismiss = {
//                                viewModel.updateEditMode(BubbleEditMode.EDIT)
                            },
                            onConfirm = { label ->
//                                viewModel.moveSelectedBubbles(label)
//                                viewModel.updateEditMode(BubbleEditMode.EDIT)
                            }
                        )
                    }

                } else if (uiState.bubbleEditMode == BubbleEditMode.DELETE) {
                    BottomSheetPopUp(
                        title = "${uiState.selectedBubbles.size} 개의 버블을 삭제하시겠습니까?",
                        cancelText = "취소",
                        confirmText = "삭제",
                        onDismiss = {
                            resetEditMode(viewModel, updateShowBottomNav)
                        },
                        onConfirm = {
                            viewModel.deleteSelectedBubbles()
                            resetEditMode(viewModel, updateShowBottomNav)
                        },
                        sheetState = sheetState,
                    )
                }
            }
        }
    }
}

private fun resetEditMode(
    viewModel: LabelDetailViewModel,
    updateShowBottomNav: (Boolean) -> Unit
) {
    viewModel.updateEditMode(BubbleEditMode.NONE)
    updateShowBottomNav(true)
}

@Composable
fun LabelTopAppBar(
    label: LabelModel,
    navHostController: NavHostController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { navHostController.popBackStack() }) {
            Icon(
                painter = painterResource(R.drawable.ic_chevron_down),
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = label.color, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "${label.name}  ${label.bubbles.size}",
            style = MaterialTheme.typography.titleLarge,
            color = Gray800
        )
    }
}
