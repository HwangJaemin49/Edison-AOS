package com.umc.edison.ui.bubblestorage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.ui.components.Bubble
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.storage.BubbleStorageMode
import com.umc.edison.presentation.storage.BubbleStorageViewModel
import com.umc.edison.ui.components.BottomSheet
import com.umc.edison.ui.components.BottomSheetForDelete
import com.umc.edison.ui.components.BottomSheetPopUp
import com.umc.edison.ui.components.BubblesLayout
import com.umc.edison.ui.components.MyEdisonNavBar
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.White000

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleStorageScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: BubbleStorageViewModel = hiltViewModel(),
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val uiState by viewModel.uiState.collectAsState()
    val isBlur =
        uiState.bubbleStorageMode != BubbleStorageMode.NONE && uiState.bubbleStorageMode != BubbleStorageMode.VIEW

    BackHandler(enabled = true) {
        if (uiState.bubbleStorageMode == BubbleStorageMode.NONE) {
            navHostController.popBackStack()
        } else {
            resetEditMode(viewModel, updateShowBottomNav)
        }
    }

    Scaffold(

        bottomBar = {
            if (uiState.bubbleStorageMode == BubbleStorageMode.EDIT) {
                BottomSheetForDelete(
                    selectedCnt = uiState.selectedBubbles.size,
                    showSelectedCnt = true,
                    onButtonClick = {
                        // 공유하기 버튼 눌렀을 때 동작
                        viewModel.updateEditMode(BubbleStorageMode.SHARE)
                    },
                    onDelete = {
                        viewModel.updateEditMode(BubbleStorageMode.DELETE)
                    },
                    buttonEnabled = uiState.selectedBubbles.isNotEmpty(),
                    buttonText = "공유하기",
                )
            }
        }
    ) { innerPadding ->
        Box(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(White000)
        )
        {
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
                var onBubbleClick: (BubbleModel) -> Unit = {}
                var onBubbleLongClick: (BubbleModel) -> Unit = {}

                if (uiState.bubbleStorageMode == BubbleStorageMode.EDIT) {
                    onBubbleClick = { bubble ->
                        viewModel.toggleSelectBubble(bubble)
                    }
                } else if (uiState.bubbleStorageMode == BubbleStorageMode.NONE) {
                    onBubbleClick = { bubble ->
                        viewModel.selectBubble(bubble)
                        viewModel.updateEditMode(BubbleStorageMode.VIEW)
                    }
                    onBubbleLongClick = { bubble ->
                        viewModel.selectBubble(bubble)
                        viewModel.updateEditMode(BubbleStorageMode.EDIT)
                        updateShowBottomNav(false)
                    }
                }

                Box(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            if (uiState.bubbleStorageMode == BubbleStorageMode.EDIT) {
                                resetEditMode(viewModel, updateShowBottomNav)
                            }
                        }
                        .fillMaxSize()
                ) {
                    BubblesLayout(
                        bubbles = uiState.bubbles,
                        onBubbleClick = onBubbleClick,
                        onBubbleLongClick = onBubbleLongClick,
                        isBlur = isBlur,
                        selectedBubble = uiState.selectedBubbles,
                    )
                    MyEdisonNavBar(
                        onProfileClicked = { /* */ },
                        onCompassClicked = { navHostController.navigate(NavRoute.BubbleStorage.route) }
                    )
                }

                if (uiState.bubbleStorageMode == BubbleStorageMode.VIEW && uiState.selectedBubbles.isNotEmpty()) {
                    val bubble = uiState.selectedBubbles.first()
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clickable(onClick = {
                                viewModel.updateEditMode(BubbleStorageMode.NONE)
                            }),
                        contentAlignment = Alignment.Center
                    ) {
                        Bubble(
                            bubble = bubble,
                            onClick = {
                                // TODO: 버블 작성 화면 구현 완료되면 연결
                                // navHostController.navigate(NavRoute.BubbleEdit.createRoute(bubble.id))
                            }
                        )
                    }
                } else if (uiState.bubbleStorageMode == BubbleStorageMode.SHARE) {
                    BottomSheet(
                        onDismiss = {
                            viewModel.updateEditMode(BubbleStorageMode.EDIT)
                        },
                        sheetState = sheetState,
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            TextButton(
                                onClick = { /* TODO: 이미지 공유 로직 추가 */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Text(
                                    text = "이미지로 공유하기",
                                    color = Color.Black
                                )
                            }

                            Divider(color = Color.LightGray, thickness = 1.dp)

                            TextButton(
                                onClick = { /* TODO: 텍스트 공유 로직 추가 */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Text(
                                    text = "텍스트로 공유하기",
                                    color = Color.Black
                                )
                            }
                        }
                    }


                } else if (uiState.bubbleStorageMode == BubbleStorageMode.DELETE) {
                    BottomSheetPopUp(
                        title = "${uiState.selectedBubbles.size} 개의 버블을 삭제하시겠습니까?",
                        cancelText = "취소",
                        confirmText = "삭제",
                        onDismiss = {
                            viewModel.updateEditMode(BubbleStorageMode.EDIT)
                        },
                        onConfirm = {
                             viewModel.deleteSelectedBubbles(showBottomNav = updateShowBottomNav)
                        },
                        sheetState = sheetState,
                    )
                }
            }
        }
    }
}

private fun resetEditMode(
    viewModel: BubbleStorageViewModel,
    updateShowBottomNav: (Boolean) -> Unit
) {
    viewModel.updateEditMode(BubbleStorageMode.NONE)
    updateShowBottomNav(true)
}
