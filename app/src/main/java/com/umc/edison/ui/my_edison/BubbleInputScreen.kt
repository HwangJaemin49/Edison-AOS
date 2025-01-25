package com.umc.edison.ui.my_edison

import androidx.compose.material3.DropdownMenu
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.platform.LocalContext
import coil3.request.ImageRequest
import coil3.size.Size
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.content.FileProvider
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.umc.edison.R
import com.umc.edison.domain.model.ContentType
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.my_edison.BubbleInputViewModel
import com.umc.edison.ui.components.BubbleDoor
import com.umc.edison.ui.theme.Gray800
import java.io.File

@Composable
fun PopupWindowDialog() {
    val openDialog = remember { mutableStateOf(false) }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Button(
            onClick = {
                openDialog.value = !openDialog.value
            }
        ) {
            Text(text = "Show Popup")
        }


    }
}

@Composable
fun RichTextEditorWithBoldToggle() {
    // RichTextEditor의 상태를 관리
    val richTextState = rememberRichTextState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Bold 토글 버튼
        Button(
            onClick = {
                richTextState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Bold")
        }

        Button(
            onClick = {
                richTextState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                // Bold 스타일 토글
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Bold")
        }

        Button(
            onClick = {
                richTextState.toggleOrderedList()
                // Bold 스타일 토글
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Bold")
        }


        // RichTextEditor
        BasicRichTextEditor(
            state = richTextState,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp)
        )
    }
}


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
    val richTextState = rememberRichTextState()
    val context = LocalContext.current


    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            selectedImageUri?.let { uri ->
                viewModel.addContentBlock(uri.toString()) // ViewModel에 URI 전달
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
            richTextState = richTextState,
            bottomPadding = 56.dp
        )
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
                contentDescription = "List",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = -23.dp)
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
                painter = painterResource(id = R.drawable.ic_bold),
                contentDescription = "Bold",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = 23.dp)
                    .clickable { richTextState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold)) }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_italic),
                contentDescription = "italic",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { listExpanded = true }
            )


            Box() {
                Image(
                    painter = painterResource(id = R.drawable.ic_underlined),
                    contentDescription = "underline",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { cameraExpanded = true }

                )
            }


            Image(
                painter = painterResource(id = R.drawable.ic_brush),
                contentDescription = "highlight",
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
                    .clickable { textExpanded = false }
            )

        }

        if (listExpanded) Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
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
                contentDescription = "List",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = -23.dp)
                    .clickable { listExpanded = false }
            )

        }
    }

}




