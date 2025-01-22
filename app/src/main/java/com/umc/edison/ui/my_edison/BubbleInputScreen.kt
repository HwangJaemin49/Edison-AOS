package com.umc.edison.ui.my_edison
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
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
import coil.compose.rememberAsyncImagePainter
import android.net.Uri
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import coil.request.ImageRequest
import coil.size.Size
import com.umc.edison.R
import com.umc.edison.domain.model.ContentType
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.my_edison.BubbleInputViewModel
import com.umc.edison.ui.components.BubbleDoor

import java.io.File

@Composable
fun BubbleInputScreen(
    viewModel: BubbleInputViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

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

    Box(modifier = Modifier.fillMaxSize()) {

        BubbleDoor(
            bubble = BubbleModel(),
            isEditable = true,
            onClick = { /* TODO: 구현 */ },
        )

        Column(modifier = Modifier.fillMaxSize()) {

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


            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),  // 아이템 간 간격 설정
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 300.dp, start = 23.dp, end = 23.dp, bottom = 10.dp)
            ) {
                itemsIndexed(uiState.bubble.contentBlocks) { index, block ->

                    var textFieldValue by remember { mutableStateOf("") }
                    val textColor = colorResource(id = R.color.gray_700)
                    val hintColor = colorResource(id = R.color.gray_500)
                    val isImageContentEmpty =
                        uiState.bubble.contentBlocks.none { it.type == ContentType.IMAGE }

                    when (block.type) {
                        ContentType.TEXT -> {
                            BasicTextField(
                                value = textFieldValue,
                                onValueChange = { newValue ->
                                    textFieldValue = newValue
                                },
                                modifier = Modifier
                                    .fillMaxWidth(), // 여백 설정
                                textStyle = TextStyle(
                                    color = textColor, // 텍스트 색상
                                    fontSize = 18.sp
                                ),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()// 내부 여백 설정
                                    ) {

                                        if (isImageContentEmpty && textFieldValue.isEmpty()) {
                                            Text(
                                                text = block.content, // Placeholder 텍스트
                                                color = hintColor, // Placeholder 색상
                                                fontSize = 18.sp
                                            )
                                        }

                                        innerTextField() // 실제 텍스트 필드 렌더링
                                    }
                                }
                            )
                        }

                        ContentType.IMAGE -> {
                            DynamicImage(block.content)
                        }
                    }


                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .imePadding(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_text_tool_off),
                    contentDescription = "Text Tool",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { expanded = true }
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_list),
                    contentDescription = "List",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(24.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "Camera",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { launcher.launch("image/*") }
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_link),
                    contentDescription = "Link",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(24.dp)
                        .clickable{

                            val tempFile = createImageFile()
                            val uri = FileProvider.getUriForFile(
                                context,
                                "${context.packageName}.provider", // AndroidManifest에 설정된 FileProvider authority
                                tempFile
                            )
                            selectedImageUri = uri
                            takePictureLauncher.launch(uri)

                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_list),
                    contentDescription = "List",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(24.dp)
                )
            }

            if (expanded) {
                TextStyleDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    onOptionSelected = { option ->
                        println("Selected Option: $option")
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DynamicImage(imageUrl: String) {
    var aspectRatio by remember { mutableStateOf(1f) } // 기본 비율 설정

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build(),
        onSuccess = { result ->
            val width = result.painter.intrinsicSize.width
            val height = result.painter.intrinsicSize.height
            if (width > 0 && height > 0) {
                aspectRatio = width / height
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth() // 가로로 꽉 채움
            .aspectRatio(aspectRatio) // 동적으로 계산된 비율 적용
    ) {
        Image(
            painter = painter,
            contentDescription = "Dynamic Image",
            contentScale = ContentScale.Crop, // 원본 비율 유지하며 꽉 채우기
            modifier = Modifier.fillMaxSize()
        )
    }
}

