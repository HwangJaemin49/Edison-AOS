package com.umc.edison.ui.my_edison

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.platform.LocalContext
import android.net.Uri
import android.text.Html
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.umc.edison.R
import com.umc.edison.domain.model.ContentType
import com.umc.edison.presentation.label.LabelEditMode
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.my_edison.BubbleInputViewModel
import com.umc.edison.ui.components.BottomSheet
import com.umc.edison.ui.components.BubbleDoor
import com.umc.edison.ui.components.LabelModalContent
import com.umc.edison.ui.navigation.NavRoute
import com.umc.edison.ui.theme.Gray900
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleInputScreen(
    navHostController: NavHostController,
    updateShowBottomNav: (Boolean) -> Unit,
    viewModel: BubbleInputViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var textExpanded by remember { mutableStateOf(false) }
    var listExpanded by remember { mutableStateOf(false) }
    var cameraExpanded by remember { mutableStateOf(false) }
    var linkExpanded by remember { mutableStateOf(false) }
    var galleryOpen by remember { mutableStateOf(false) }
    var cameraOpen by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val selectedImages by remember { mutableStateOf(mutableListOf<Uri>()) }
    var isBoldActive by remember { mutableStateOf(false) }
    var isItalicActive by remember { mutableStateOf(false) }
    var isUnderlineActive by remember { mutableStateOf(false) }
    var isHighlightActive by remember { mutableStateOf(false) }
    var isListActive by remember { mutableStateOf(false) }
    var isOrderedListActive by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var isBottomSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var linkDropDownExpanded by remember { mutableStateOf(false) }
    var selectedBubbles by remember { mutableStateOf(mutableListOf<BubbleModel>()) }
    var addLink by remember { mutableStateOf(false) }
    var selectedTitle by remember { mutableStateOf("") }
    var selectedId by remember { mutableStateOf(0) }
    var addLinkBubble by remember { mutableStateOf(false) }


    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            if (selectedImages.size < 10){
                selectedImageUri?.let { uri ->
                    val savedUri = saveImageToInternalStorage(context, uri)
                    selectedImages.add(savedUri)
                    viewModel.addContentBlock(savedUri.toString())
                }

            } else {
                Toast.makeText(context, "최대 10장까지 첨부할 수 있습니다.", Toast.LENGTH_SHORT).show()
            }

        }
    }

    val createImageFile: () -> File = {
        val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        tempFile.apply {
            createNewFile()
            deleteOnExit()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            if (selectedImages.size < 10) {
                val savedUri = saveImageToInternalStorage(context, it)
                selectedImages.add(savedUri)
                viewModel.addContentBlock(savedUri.toString())
            } else {
                Toast.makeText(context, "최대 10장까지 첨부할 수 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (uiState.labelEditMode == LabelEditMode.ADD) {
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

                    val updatedLabels = uiState.bubble.labels.toMutableList().apply { add(label) }
                    viewModel.updateLabel(updatedLabels)
                    viewModel.confirmLabelModal(label)
                },
                label = uiState.selectedLabel
            )
        }
    }

    if (isBottomSheetOpen) {

        println("확인2"+uiState.bubble.labels)
        BottomSheet(
            onDismiss = { isBottomSheetOpen = false },
            sheetState = sheetState,
        ) {
            LabelSelectModal(
                labels = uiState.labels ,
                selectedLabels = uiState.bubble.labels,
                onConfirm = { selectedLabelsFromModal ->
                    viewModel.updateLabel(selectedLabelsFromModal)
                },
                onAddLabelClick = {
                    viewModel.updateEditMode(LabelEditMode.ADD)
                    isBottomSheetOpen = false
                                  },
                onDismiss = {
                    isBottomSheetOpen = false
                }
            )
        }
    }

    LaunchedEffect(Unit) { updateShowBottomNav(false) }


    BackHandler {
        updateShowBottomNav(true)
        navHostController.popBackStack()
        viewModel.saveBubble()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )


    {
        BubbleDoor(
            bubble = uiState.bubble,
            isEditable = true,
            onClick = { /* TODO: 구현 */ },
            onBubbleChange = { updatedBubble ->
                viewModel.updateBubble(updatedBubble)
                println(updatedBubble)
            },
            isBoldActive = isBoldActive,
            isItalicActive = isItalicActive,
            isUnderlineActive = isUnderlineActive,
            isHighlightActive = isHighlightActive,
            isListActive = isListActive,
            isOrderedListActive = isOrderedListActive,
            bottomPadding = 56.dp,
            addLink = addLink,
            selectedTitle = selectedTitle,
            selectedId = selectedId,
            onAddLinkHandled = { addLink = false },
            deleteClicked = { contentBlock ->
                viewModel.deleteContentBlock(contentBlock)
            },
            linkClicked = {bubbleId->
                println("콜백함수"+bubbleId)
                viewModel.fetchBubble(bubbleId)
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(28.dp)
                    .offset(x = 23.dp, y = 20.dp)
                    .clickable{
                        viewModel.saveBubble()
                        navHostController.navigate(NavRoute.MyEdison.route)
                        updateShowBottomNav(true)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = "Save",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(28.dp)
                    .offset(x = -23.dp, y = 20.dp)
                    .clickable {

                        if (uiState.bubble.contentBlocks.isEmpty() || uiState.bubble.contentBlocks.all { it.content.isBlank() }) {
                            Toast.makeText(context, "내용이 없습니다.", Toast.LENGTH_SHORT).show()
                            return@clickable
                        }

                        viewModel.saveBubble()
//                        navHostController.navigate(NavRoute.MyEdison.route)
//                        updateShowBottomNav(true)
                    }
            )


        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
                .offset(y = -56.dp)
                .imePadding(),
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
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .imePadding()
                .padding(vertical = 13.dp, horizontal = 16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_text_tool_off),
                contentDescription = "Text Tool",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = 23.dp)
                    .clickable { textExpanded = true }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_list),
                contentDescription = "List",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { listExpanded = true }
            )
            Box() {
                Image(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "Camera",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { cameraExpanded = true }
                )
                CameraPopup(
                    cameraExpanded = cameraExpanded,
                    onGalleryOpen = {
                        galleryOpen = true
                        cameraExpanded = false
                    },
                    onCameraOpen = {
                        cameraOpen = true
                        cameraExpanded = false
                    },
                    onDismiss = {
                        cameraExpanded = false
                    }
                )
            }
            if (galleryOpen) {
                launcher.launch("image/*")
                galleryOpen = false
            }
            if (cameraOpen) {
                val tempFile = createImageFile()
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    tempFile
                )
                selectedImageUri = uri
                takePictureLauncher.launch(uri)
                cameraOpen = false
            }

            var selectedBubbleId by remember { mutableStateOf(0) }
            var selectedBubbleTitle by remember { mutableStateOf<String?>(null) }



            Box() {
                Image(
                    painter = painterResource(id = R.drawable.ic_link),
                    contentDescription = "Link",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            linkExpanded = true
                        }
                )
                LinkPopup(linkExpanded = linkExpanded,
                    onDismiss = {
                        linkExpanded = false
                    },
                    backLink = {
                        linkExpanded = false
                        linkDropDownExpanded = true
                    },
                    linkBubble = {
                        linkExpanded = false
                        val previousBubble = uiState.bubble
                        selectedBubbleId = uiState.bubble.id
                        selectedBubbleTitle = uiState.bubble.title

                        if (!selectedBubbles.contains(previousBubble)) {
                            selectedBubbles = selectedBubbles.toMutableList().apply {
                                add(previousBubble)
                            }
                        }

                        viewModel.updateBubbleWithLink()
                        addLinkBubble = true
                    }
                )



                if (addLinkBubble) {
                    addLinkBubble = false
                    val lastTextBlock = uiState.bubble.contentBlocks
                        .filter { it.type == ContentType.TEXT }
                        .lastOrNull()

                    if (lastTextBlock != null) {
                        selectedTitle = selectedBubbleTitle.toString()
                        selectedId = selectedBubbleId
                        addLink = true
                    }
                }

                DropdownMenu(
                    expanded = linkDropDownExpanded,
                    onDismissRequest = { linkDropDownExpanded = false },
                    modifier = Modifier.heightIn(max = 300.dp)
                                       .background(Color.White)
                ) {

                    val bubbles = uiState.bubbles


                    bubbles.forEach { bubble ->
                        DropdownMenuItem(
                            text = {Text(
                                bubble.title?.takeIf { it.isNotBlank() }
                                    ?: bubble.contentBlocks
                                        .filter { it.type == ContentType.TEXT }
                                        .firstOrNull { it.content.parseHtml().isNotBlank() }
                                        ?.content?.parseHtml()?.take(5)
                                    ?: "내용 없음"
                            )},
                            onClick = {
                                println("Selected Bubble Title: ${bubble.title}")
                                if (!selectedBubbles.contains(bubble)) {
                                    selectedBubbles = selectedBubbles.toMutableList().apply {
                                        add(bubble)
                                    }
                                    selectedTitle = bubble.title?.takeIf { it.isNotBlank() }
                                        ?: bubble.contentBlocks
                                            .filter { it.type == ContentType.TEXT }
                                            .firstOrNull { it.content.parseHtml().isNotBlank() }
                                            ?.content?.parseHtml()?.take(5)
                                                ?: "내용 없음"
                                    selectedId = bubble.id
                                    addLink = true
                                    linkDropDownExpanded = false
                                }
                            }
                        )
                    }
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_tag),
                contentDescription = "Label",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = -23.dp)
                    .clickable { isBottomSheetOpen = true }
            )
        }

        if (textExpanded) Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .imePadding()
                .padding(vertical = 13.dp, horizontal = 16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(if(isBoldActive) R.drawable.ic_bold else R.drawable.ic_bold_off),
                contentDescription = "Bold",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = 23.dp)
                    .clickable { isBoldActive = !isBoldActive }
            )

            Image(
                painter = painterResource(if(isItalicActive) R.drawable.ic_italic else R.drawable.ic_italic_off),
                contentDescription = "italic",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { isItalicActive = !isItalicActive }
            )


            Box() {
                Image(
                    painter = painterResource(if(isUnderlineActive) R.drawable.ic_underline else R.drawable.ic_underline_off),
                    contentDescription = "underline",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { isUnderlineActive = !isUnderlineActive }

                )
            }


            Image(
                painter = painterResource(
                    id = if (isHighlightActive) R.drawable.ic_highlight else R.drawable.ic_highlight_off
                    ),
                contentDescription = "highlight",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { isHighlightActive = !isHighlightActive }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_x),
                contentDescription = "close",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = -23.dp)
                    .clickable {
                        textExpanded = false
                        isBoldActive =false
                        isItalicActive =false
                        isUnderlineActive = false
                        isHighlightActive = false
                    }
            )

        }

        if (listExpanded) Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .imePadding()
                .padding(vertical = 13.dp, horizontal = 16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter =  painterResource(
                    id = if (isListActive) R.drawable.ic_list_tool_off else R.drawable.ic_list_tool_off1
                ),
                contentDescription = "list add",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = 23.dp)
                    .clickable { isListActive = !isListActive }
            )

            Image(
                painter = painterResource(id = if(isOrderedListActive) R.drawable.ic_number else R.drawable.ic_number_off),
                contentDescription = "List num",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { isOrderedListActive = !isOrderedListActive }
            )


            Box() {
                Image(
                    painter = painterResource(id = R.drawable.ic_empty),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { }

                )
            }


            Image(
                painter = painterResource(id = R.drawable.ic_empty),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {

                    }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_x),
                contentDescription = "close",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = -23.dp)
                    .clickable {
                        listExpanded = false
                        isListActive = false
                        isOrderedListActive = false
                    }
            )

        }


    }

}

fun String.parseHtml(): String {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
}

fun saveImageToInternalStorage(context: Context, uri: Uri): Uri {
    val inputStream = context.contentResolver.openInputStream(uri)

    val fileName = "image_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)

    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()

    return Uri.fromFile(file)
}