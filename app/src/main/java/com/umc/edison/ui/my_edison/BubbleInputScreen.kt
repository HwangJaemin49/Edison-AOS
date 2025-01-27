package com.umc.edison.ui.my_edison

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.umc.edison.R
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.presentation.my_edison.BubbleInputViewModel
import com.umc.edison.ui.components.BubbleDoor
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray900
import com.umc.edison.ui.theme.Pink400
import com.umc.edison.ui.theme.White000
import com.umc.edison.ui.theme.Yellow100
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleInputScreen(

    viewModel: BubbleInputViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var textExpanded by remember { mutableStateOf(false) }
    var listExpanded by remember { mutableStateOf(false) }
    var cameraExpanded by remember { mutableStateOf(false) }
    var galleryOpen by remember { mutableStateOf(false) }
    var cameraOpen by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isBoldActive by remember { mutableStateOf(false) }
    var isItalicActive by remember { mutableStateOf(false) }
    var isUnderlineActive by remember { mutableStateOf(false) }
    var isHighlightActive by remember { mutableStateOf(false) }
    var isListActive by remember { mutableStateOf(false) }
    var isOrderedListActive by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var isBottomSheetOpen by remember { mutableStateOf(false) }
    var currentLabel by remember { mutableStateOf(LabelModel(name = "", color = Yellow100)) }
    var selectedLabels by remember { mutableStateOf(mutableSetOf<LabelModel>()) }


    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true, )

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            selectedImageUri?.let { uri ->
                viewModel.addContentBlock(uri.toString())
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
            viewModel.addContentBlock(it.toString()) // ViewModel에 전달
            selectedImageUri = it // 선택된 URI 저장
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
    {

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
            )

            Image(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = "More",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(28.dp)
                    .offset(y = 20.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = "Save",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(28.dp)
                    .offset(x = -23.dp, y = 20.dp)
                    .clickable { println("Save button clicked") }
            )
        }
        BubbleDoor(
            bubble = uiState.bubble,
            isEditable = true,
            onClick = { /* TODO: 구현 */ },
            onBubbleChange = { updatedBubble ->
                viewModel.updateBubble(updatedBubble)
            },
            isBoldActive = isBoldActive,
            isItalicActive=isItalicActive,
            isUnderlineActive=isUnderlineActive,
            isHighlightActive=isHighlightActive,
            isListActive=isListActive,
            isOrderedListActive=isOrderedListActive,
            bottomPadding = 56.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
                .offset(y=-56.dp)
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
                CameraPopup(CameraExpanded = cameraExpanded,
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


            Image(
                painter = painterResource(id = R.drawable.ic_link),
                contentDescription = "Link",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {

                    }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_tag),
                contentDescription = "Label",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = -23.dp)
                    .clickable{  isBottomSheetOpen = true}
            )
        }

        if (isBottomSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = { isBottomSheetOpen = false },
                sheetState = sheetState,
                containerColor = White000,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ) {
                LabelSelectModal(
                    labels = uiState.labels, // Room에서 가져온 데이터를 전달
                    selectedLabels = uiState.bubble.labels, // 이미 선택된 라벨
                    onConfirm = { selectedLabelsFromModal ->
                        viewModel.updateLabel(selectedLabelsFromModal)
                        println("Selected Labels: $selectedLabels")

                    },
                    onDismiss = {
                        isBottomSheetOpen = false
                    }
                )
            }
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
                painter = painterResource(id = R.drawable.ic_bold),
                contentDescription = "Bold",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = 23.dp)
                    . clickable { isBoldActive = !isBoldActive }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_italic),
                contentDescription = "italic",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {  isItalicActive=!isItalicActive}
            )


            Box() {
                Image(
                    painter = painterResource(id = R.drawable.ic_underlined),
                    contentDescription = "underline",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { isUnderlineActive=!isUnderlineActive}

                )
            }


            Image(
                painter = painterResource(id = R.drawable.ic_brush),
                contentDescription = "highlight",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { isHighlightActive=!isHighlightActive}
            )

            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "close",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = -23.dp)
                    .clickable { textExpanded = false }
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
                painter = painterResource(id = R.drawable.ic_list_add),
                contentDescription = "list add",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = 23.dp)
                    .clickable { isListActive=!isListActive}
            )

            Image(
                painter = painterResource(id = R.drawable.ic_list_num),
                contentDescription = "List num",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { isOrderedListActive=!isOrderedListActive}
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
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "close",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = -23.dp)
                    .clickable { listExpanded = false }
            )

        }




    }

}




