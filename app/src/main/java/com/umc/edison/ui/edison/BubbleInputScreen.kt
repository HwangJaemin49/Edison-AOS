package com.umc.edison.ui.edison

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.presentation.label.LabelEditMode
import com.umc.edison.presentation.edison.BubbleInputViewModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.BaseContent
import com.umc.edison.ui.components.BottomSheet
import com.umc.edison.ui.components.BubbleDoor
import com.umc.edison.ui.components.IconType
import com.umc.edison.ui.components.ImageGallery
import com.umc.edison.ui.components.LabelModalContent
import com.umc.edison.ui.components.Toolbar
import com.umc.edison.ui.label.LabelSelectModalContent
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.Gray900
import com.umc.edison.ui.theme.White000
import java.io.File

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
        } else if(uiState.isGalleryOpen) {
            viewModel.closeGallery()
            viewModel.updateIcon(IconType.NONE)
        } else if (uiState.isCameraOpen) {
            viewModel.updateCameraOpen(false)
            viewModel.updateIcon(IconType.NONE)
        } else if (uiState.selectedIcon == IconType.CAMERA || uiState.selectedIcon == IconType.LINK
            || uiState.selectedIcon == IconType.BACK_LINK) {
            viewModel.updateIcon(IconType.NONE)
        } else {
            if (uiState.canSave) {
                viewModel.saveBubble(false)
            }
            navHostController.popBackStack()
        }
    }

    BaseContent(
        uiState = uiState,
        onDismiss = { viewModel.clearToastMessage() },
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
                confirmButtonEnabled = uiState.canSave
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
                    },
                    onCameraOpen = {
                        viewModel.updateCameraOpen(true)
                    },
                    onBackLinkClick = { bubble ->
                        viewModel.addBackLink(bubble)
                    },
                    onLinkBubbleClick = {
                        viewModel.updateBubbleWithLink()
                    }
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            BubbleInputContent(viewModel)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                uiState.bubble.labels.forEach { label ->
                    Box(
                        modifier = Modifier
                            .height(41.dp)
                            .background(label.color, RoundedCornerShape(20.dp))
                            .padding(horizontal = 16.dp)

                    ) {
                        Text(
                            text = label.name,
                            color = Gray900,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
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
    val context = LocalContext.current

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            viewModel.saveCameraImage(context)
        }
    }

    val createImageFile: @Composable () -> File = {
        val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        tempFile.apply {
            createNewFile()
            deleteOnExit()
        }
    }

    if (uiState.isGalleryOpen) {
        ImageGallery(
            onImageSelected = { uriList ->
                viewModel.addContentBlocks(uriList)
            },
            onClose = { viewModel.closeGallery() },
            multiSelectMode = true
        )
    }

    if (uiState.isCameraOpen) {
        val tempFile = createImageFile()
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            tempFile
        )
        viewModel.saveCameraImage(uri)
        takePictureLauncher.launch(uri)
        viewModel.updateCameraOpen(false)
    }

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
                },
                onConfirm = { label ->
                    viewModel.saveLabel(label)
                    viewModel.updateLabelEditMode(LabelEditMode.EDIT)
                },
                label = LabelModel.DEFAULT
            )
        }
    }

    if (uiState.labelEditMode == LabelEditMode.EDIT) {
        BottomSheet(
            onDismiss = { viewModel.updateLabelEditMode(LabelEditMode.NONE) },
        ) {
            LabelSelectModalContent(labels = uiState.labels,
                initSelectedLabels = uiState.bubble.labels,
                multiSelectMode = true,
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
                }
            )
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
}
