package com.umc.edison.ui.mypage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.mypage.BubbleRecoverMode
import com.umc.edison.presentation.mypage.TrashState
import com.umc.edison.presentation.mypage.TrashViewModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BackButtonTopBar
import com.umc.edison.ui.components.BottomSheetForDelete
import com.umc.edison.ui.components.BottomSheetPopUp
import com.umc.edison.ui.components.Bubble
import com.umc.edison.ui.components.CheckBoxButton
import com.umc.edison.ui.components.RadioButton
import com.umc.edison.ui.components.extractPlainText
import com.umc.edison.ui.theme.Gray100
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800

@Composable
fun TrashScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: TrashViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val baseState by viewModel.baseState.collectAsState()

    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
        viewModel.fetchDeletedBubbles()
    }

    BackHandler {
        if (uiState.mode != BubbleRecoverMode.NONE) {
            viewModel.updateBubbleRecoverMode(BubbleRecoverMode.NONE)
        } else {
            navHostController.popBackStack()
        }
    }

    BaseContent(
        baseState = baseState,
        bottomBar = {
            if (uiState.mode == BubbleRecoverMode.SELECT) {
                BottomSheetForDelete(
                    onButtonClick = { viewModel.recoverBubbles() },
                    onDelete = { viewModel.updateBubbleRecoverMode(BubbleRecoverMode.DELETE) },
                    buttonEnabled = uiState.selectedBubbles.isNotEmpty(),
                    buttonText = if (uiState.selectedBubbles.size == uiState.bubbles.size) {
                        "모두 복원"
                    } else {
                        "복원"
                    },
                )
            }
        },
    ) {
        TrashContent(
            viewModel = viewModel,
            uiState = uiState,
            navHostController = navHostController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrashContent(
    viewModel: TrashViewModel,
    uiState: TrashState,
    navHostController: NavHostController
) {
    var onBubbleClick: (BubbleModel) -> Unit = {}
    var onBubbleLongClick: (BubbleModel) -> Unit = {}

    if (uiState.mode == BubbleRecoverMode.SELECT) {
        onBubbleClick = { viewModel.toggleBubbleSelection(it) }
    } else if (uiState.mode == BubbleRecoverMode.NONE) {
        onBubbleClick = {
            viewModel.updateBubbleRecoverMode(BubbleRecoverMode.VIEW)
            viewModel.selectBubble(it)
        }
        onBubbleLongClick = {
            viewModel.updateBubbleRecoverMode(BubbleRecoverMode.SELECT)
            viewModel.toggleBubbleSelection(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        BackButtonTopBar(
            onBack = { navHostController.popBackStack() },
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "휴지통",
                    style = MaterialTheme.typography.titleLarge,
                    color = Gray800,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                )

                Text(
                    text = "삭제된 버블은 30일간 보관됩니다.",
                    style = MaterialTheme.typography.titleSmall,
                    color = Gray500
                )
            }
        }

        if (uiState.mode != BubbleRecoverMode.NONE && uiState.mode != BubbleRecoverMode.VIEW) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = uiState.selectedBubbles.size == uiState.bubbles.size,
                    onClick = { viewModel.selectAllBubbles() }
                )

                Text(
                    text = "전체",
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray800
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "버블 선택",
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray800
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = if (uiState.mode == BubbleRecoverMode.NONE || uiState.mode == BubbleRecoverMode.VIEW) {
                Arrangement.spacedBy(0.dp)
            } else {
                Arrangement.spacedBy(8.dp)
            }
        ) {
            items(uiState.bubbles) { bubble ->
                TrashItem(
                    bubble = bubble,
                    uiState = uiState,
                    onBubbleClick = onBubbleClick,
                    onBubbleLongClick = onBubbleLongClick
                )
            }
        }
    }

    if (uiState.mode == BubbleRecoverMode.DELETE) {
        BottomSheetPopUp(
            title = "클라우드와 모든 기기에서 완전히 삭제됩니다.",
            cancelText = "취소",
            confirmText = "삭제",
            onDismiss = { viewModel.updateBubbleRecoverMode(BubbleRecoverMode.SELECT) },
            onConfirm = { viewModel.deleteBubbles() },
        )
    }

    if (uiState.mode == BubbleRecoverMode.VIEW) {
        val bubble = uiState.selectedBubbles.first()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(onClick = {
                    viewModel.updateBubbleRecoverMode(BubbleRecoverMode.NONE)
                }),
            contentAlignment = Alignment.Center
        ) {
            Bubble(
                bubble = bubble,
                onBubbleClick = {},
                onLinkedBubbleClick = {}
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TrashItem(
    bubble: BubbleModel,
    uiState: TrashState,
    onBubbleClick: (BubbleModel) -> Unit,
    onBubbleLongClick: (BubbleModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (uiState.mode == BubbleRecoverMode.SELECT && uiState.selectedBubbles.contains(bubble)) {
                        Gray100
                    } else {
                        Color.Transparent
                    }
                )
                .combinedClickable(
                    onClick = { onBubbleClick(bubble) },
                    onLongClick = { onBubbleLongClick(bubble) }
                )
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text =
                extractPlainText(bubble).first.ifEmpty { "내용 없음" },
                style = MaterialTheme.typography.bodySmall,
                color = Gray800,
                modifier = Modifier.weight(1f)
            )

            if (uiState.mode != BubbleRecoverMode.NONE && uiState.mode != BubbleRecoverMode.VIEW) {
                CheckBoxButton(
                    selected = uiState.selectedBubbles.contains(bubble),
                    onClick = { onBubbleClick(bubble) }
                )
            }
        }

        if (uiState.mode == BubbleRecoverMode.NONE || uiState.mode == BubbleRecoverMode.VIEW) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray100)
            )
        }
    }
}
