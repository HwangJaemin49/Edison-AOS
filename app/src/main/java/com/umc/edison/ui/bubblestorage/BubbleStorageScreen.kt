package com.umc.edison.ui.bubblestorage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.umc.edison.ui.LoadingScreen
import com.umc.edison.ui.components.BottomSheet
import com.umc.edison.ui.components.BottomSheetForDelete
import com.umc.edison.ui.components.BottomSheetPopUp
import com.umc.edison.ui.components.BubblesLayout
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray900
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
                LoadingScreen()
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
                }

                if (uiState.bubbleStorageMode == BubbleStorageMode.VIEW && uiState.selectedBubbles.isNotEmpty()) {
                    val bubble = uiState.selectedBubbles.first()
                    Bubble(
                        bubble = bubble,
                        onBackScreenClick = {
                            viewModel.updateEditMode(BubbleStorageMode.NONE)
                        },
                        onBubbleClick = {
                            // TODO: 버블 클릭 시 동작 추가
                        },
                    )
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
                                    .padding(horizontal = 16.dp, vertical = 10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "이미지로 공유하기",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Gray900
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            ) {
                                Divider(
                                    color = Gray300,
                                    thickness = 1.dp,
                                    modifier = Modifier.width(326.dp)
                                )
                            }

                            TextButton(
                                onClick = { /* TODO: 텍스트 공유 로직 추가 */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 10.dp)

                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "텍스트로 공유하기",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Gray900
                                    )
                                }
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
