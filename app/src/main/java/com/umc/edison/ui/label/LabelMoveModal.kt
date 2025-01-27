package com.umc.edison.ui.label

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.components.LabelListItemForSelect
import com.umc.edison.ui.components.MiddleCancelButton
import com.umc.edison.ui.components.MiddleConfirmButton
import com.umc.edison.ui.theme.Gray800

@Composable
fun LabelMoveModalContent(
    onDismiss: () -> Unit,
    onConfirm: (LabelModel) -> Unit,
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
            modifier = Modifier.padding(bottom = 18.dp)
        )

        // 라벨 리스트
        LazyColumn(
            modifier = Modifier.height(450.dp)
        ) {
            items(labels.size) { index ->
                val label = labels[index]
                LabelListItemForSelect(
                    label = label,
                    selected = selectedLabel == label,
                    multiSelectMode = false,
                    onClick = {
                        selectedLabel = label
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 하단 버튼들
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 17.dp, end = 27.dp, bottom = 17.dp),
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
                    onConfirm(selectedLabel!!)
                },
                enabled = selectedLabel != null,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
