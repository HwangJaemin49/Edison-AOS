package com.umc.edison.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umc.edison.presentation.label.LabelEditMode
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.theme.EdisonTheme
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000
import com.umc.edison.ui.theme.Yellow100
import com.umc.edison.ui.theme.ColorPickerList
import com.umc.edison.ui.theme.Gray300

@Composable
fun LabelModalContent(
    editMode: LabelEditMode,
    onDismiss: () -> Unit,
    onConfirm: (LabelModel) -> Unit,
    label: LabelModel,
) {
    var selectedColor by remember { mutableStateOf(label.color) }
    val colors = ColorPickerList
    var text by remember { mutableStateOf(label.name) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                    .wrapContentHeight()
                    .background(White000),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Gray800,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Gray800,
                    unfocusedTextColor = Gray800
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
                modifier = Modifier.weight(1f)
            )

            MiddleConfirmButton(
                text = if (editMode == LabelEditMode.ADD) "생성" else "확인",
                onClick = {
                    if (text.length > 20) {
                        errorMessage = "라벨 이름은 20자 이하로 입력해주세요"
                        return@MiddleConfirmButton
                    }
                    onConfirm(label.copy(name = text, color = selectedColor))
                },
                enabled = validateLabelInfo(text, selectedColor),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun validateLabelInfo(text: String, selectedColor: Color): Boolean {
    return text.isNotBlank() && selectedColor != Gray300
}
