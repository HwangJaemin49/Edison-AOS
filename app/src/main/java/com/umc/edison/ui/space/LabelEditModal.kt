package com.umc.edison.ui.space

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.components.MiddleCancelButton
import com.umc.edison.ui.components.MiddleConfirmButton
import com.umc.edison.ui.theme.EdisonTheme
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000
import com.umc.edison.ui.theme.Yellow100

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelModalContent(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    label: LabelModel,
    onLabelChange: (LabelModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White000)
            .padding(start = 24.dp, top = 32.dp, end = 24.dp, bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = label.name,
                textStyle = MaterialTheme.typography.displayLarge,
                onValueChange = { updatedName ->
                    onLabelChange(label.copy(name = updatedName))
                },
                placeholder = {
                    Text(
                        text = "라벨 이름",
                        style = MaterialTheme.typography.displayLarge,
                        color = Gray500
                    )
                },
                modifier = Modifier.weight(1f).wrapContentHeight(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = White000,
                    focusedTextColor = Gray800,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Box(
                modifier = Modifier
                    .width(60.dp)
                    .fillMaxHeight()
                    .background(label.color, shape = RoundedCornerShape(15.dp))
            )
        }

        // 컬러 선택 UI
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Text("컬러 선택 UI")
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                text = "확인",
                onClick = onConfirm,
                enabled = label.name.isNotBlank(), // 이름이 비어 있으면 비활성화
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewModalContent() {
    EdisonTheme {
        LabelModalContent(
            onDismiss = { },
            onConfirm = { },
            label = LabelModel(name = "라벨 이름", color = Yellow100),
            onLabelChange = { }
        )
    }
}
