package com.umc.edison.ui.label

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.components.MiddleCancelButton
import com.umc.edison.ui.components.MiddleConfirmButton
import com.umc.edison.ui.theme.Gray300
import com.umc.edison.ui.theme.Gray800

@Composable
fun LabelMoveModalContent(
    onDismiss: () -> Unit,
    onConfirm: (LabelModel?) -> Unit,
    labels: List<LabelModel>
) {
    var selectedLabel by remember { mutableStateOf<LabelModel?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 27.dp)
    ) {
        Text(
            text = "라벨 선택",
            style = MaterialTheme.typography.displaySmall,
            color = Gray800,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 라벨 리스트
        labels.forEach { label ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .clickable { selectedLabel = label },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .size(60.dp)
                        .background(color = label.color, shape = RoundedCornerShape(15.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = label.name,
                            style = MaterialTheme.typography.titleLarge,
                            color = Gray800,
                            modifier = Modifier.weight(1f)
                        )

                        RadioButton(
                            selected = selectedLabel == label,
                            onClick = { selectedLabel = label }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Gray300)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 하단 버튼들
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MiddleCancelButton(
                text = "닫기",
                onClick = onDismiss,
                modifier = Modifier.weight(1f)
            )

            MiddleConfirmButton(
                text = "선택하기",
                onClick = {
                    onConfirm(selectedLabel)
                },
                enabled = selectedLabel != null,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
