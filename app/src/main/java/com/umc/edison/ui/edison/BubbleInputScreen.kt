package com.umc.edison.ui.edison

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.label.LabelEditMode
import com.umc.edison.presentation.edison.BubbleInputViewModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BottomSheet
import com.umc.edison.ui.components.BubbleDoor
import com.umc.edison.ui.components.ImageGallery
import com.umc.edison.ui.components.LabelModalContent
import com.umc.edison.ui.components.Toolbar
import com.umc.edison.ui.label.LabelSelectModalContent
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000

@Composable
fun BubbleInputScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: BubbleInputViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        updateShowBottomNav(false)
    }

    BackHandler {
        if (uiState.labelEditMode != LabelEditMode.NONE) {
            viewModel.updateLabelEditMode(LabelEditMode.NONE)
        } else {
            if (viewModel.checkCanSave()) {
                viewModel.saveBubble(false)
            }
            navHostController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            BubbleInputTopBar(
                onBackClicked = {
                    updateShowBottomNav(true)
                    navHostController.popBackStack()
                },
                onConfirmClicked = {
                    updateShowBottomNav(true)
                    viewModel.saveBubble(false)
                    navHostController.navigate(NavRoute.BubbleStorage.route)
                },
                confirmButtonEnabled = viewModel.checkCanSave()
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White000)
                    .imePadding()
            ) {
                Toolbar(
                    uiState = uiState,
                    onIconClicked = { iconType ->
                        viewModel.updateIcon(iconType)
                    },
                    onTextStylesClicked = { textStyle ->
                        viewModel.updateTextStyle(textStyle)
                    },
                    onListStyleClicked = { listStyle ->
                        viewModel.updateListStyle(listStyle)
                    },
                    onGalleryOpen = {
                        viewModel.openGallery()
                    }
                )
            }
        }
    ) { innerPadding ->
        BaseContent(
            uiState = uiState,
            onDismiss = { viewModel.clearToastMessage() },
            modifier = Modifier.padding(innerPadding)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                BubbleInputContent(viewModel)
            }
        }
    }
}

@Composable
fun BubbleInputTopBar(
    onBackClicked: () -> Unit, onConfirmClicked: () -> Unit, confirmButtonEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(White000)
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        IconButton(
            onClick = { onBackClicked() }, modifier = Modifier.size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "뒤로가기",
                tint = Gray500
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { onConfirmClicked() },
            modifier = Modifier.size(24.dp),
            enabled = confirmButtonEnabled
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = "확인",
                tint = if (confirmButtonEnabled) Gray800 else Gray500
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleInputContent(
    viewModel: BubbleInputViewModel,
) {

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.labelEditMode == LabelEditMode.ADD) {
        BottomSheet(
            onDismiss = {
                viewModel.updateLabelEditMode(LabelEditMode.NONE)
            },
        ) {
            LabelModalContent(
                editMode = uiState.labelEditMode,
                onDismiss = {
                    viewModel.updateLabelEditMode(LabelEditMode.EDIT)
                }, onConfirm = { label ->
                    viewModel.saveLabel(label)
                }, label = LabelModel.DEFAULT
            )
        }
    }

    if (uiState.labelEditMode == LabelEditMode.EDIT) {
        BottomSheet(
            onDismiss = { viewModel.updateLabelEditMode(LabelEditMode.NONE) },
        ) {
            LabelSelectModalContent(labels = uiState.labels,
                initSelectedLabels = uiState.bubble.labels,
                onConfirm = { selectedLabelsFromModal ->
                    viewModel.updateLabel(selectedLabelsFromModal)
                },
                onAddLabelClicked = {
                    viewModel.updateLabelEditMode(LabelEditMode.ADD)
                },
                onDismiss = {
                    viewModel.updateLabelEditMode(LabelEditMode.NONE)
                },
                onItemClicked = { label ->
                    viewModel.toggleLabelSelection(label)
                })
        }
    }

    BubbleDoor(
        bubble = uiState.bubble,
        isEditable = true,
        onBubbleUpdate = { bubble ->
            viewModel.updateBubbleContent(bubble)
        },
        onImageDeleted = { contentBlock ->
            viewModel.deleteContentBlock(contentBlock)
        },
        bubbleInputState = uiState,
    )

    if (uiState.isGalleryOpen) {
        ImageGallery(
            onImageSelected = { uriList ->
                viewModel.addContentBlocks(uriList)
            },
            onClose = { viewModel.closeGallery() },
            multiSelectMode = true
        )
    }
}
