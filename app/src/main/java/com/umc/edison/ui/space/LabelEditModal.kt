package com.umc.edison.ui.space

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umc.edison.R
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.components.MiddleCancelButton
import com.umc.edison.ui.components.MiddleConfirmButton
import com.umc.edison.ui.theme.EdisonTheme
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000
import com.umc.edison.ui.theme.Yellow100
import com.umc.edison.ui.theme.ColorPickerList
import com.umc.edison.ui.theme.Gray300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelModalContent(
    editMode: EditMode,
    onDismiss: () -> Unit,
    onConfirm: (LabelModel) -> Unit,
    label: LabelModel,
) {
    var selectedColor by remember { mutableStateOf(label.color) }
    val colors = ColorPickerList
    var text by remember { mutableStateOf(label.name) }

    val snackbarHostState = remember { SnackbarHostState() }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            errorMessage = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White000)
            .padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
            .imePadding()
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                textStyle = MaterialTheme.typography.displayLarge,
                onValueChange = { text = it },
                placeholder = {
                    Text(
                        text = "라벨 이름",
                        style = MaterialTheme.typography.displayLarge,
                        color = Gray500
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = White000,
                    focusedTextColor = Gray800,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(32.dp)
                    .background(selectedColor, shape = RoundedCornerShape(15.dp)),
            )
        }

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 28.dp)
        ) {
            ColorPalette(
                colors = colors,
                onColorSelected = { selectedColor = it }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MiddleCancelButton(
                text = "취소",
                onClick = onDismiss,
                enabled = true,
                modifier = Modifier.weight(1f)
            )

            MiddleConfirmButton(
                text = if (editMode == EditMode.ADD) "생성" else "확인",
                onClick = {
                    if (text.length > 20) {
                        errorMessage = "라벨 이름은 20자 이하로 입력해주세요"
                        return@MiddleConfirmButton
                    }
                    onConfirm(label.copy(name = text, color = selectedColor))
                },
                enabled = validateLabelInfo(label),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun validateLabelInfo(label: LabelModel): Boolean {
    return label.name.isNotBlank() && label.color != Gray300
}

@Composable
fun ColorPalette(
    colors: List<Color>,
    onColorSelected: (Color) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(22.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_color_palette),
                contentDescription = "Image to extract colors",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(11.dp),
        ) {
            colors.chunked(6).forEach { rowColors ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowColors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(13.dp))
                                .background(color)
                                .clickable {
                                    onColorSelected(color)
                                }
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewModalContent() {
    EdisonTheme {
        LabelModalContent(
            editMode = EditMode.ADD,
            onDismiss = { },
            onConfirm = { },
            label = LabelModel(name = "라벨 이름", color = Yellow100),
        )
    }
}
