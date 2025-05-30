package com.umc.edison.ui.label

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.components.AddLabelButton
import com.umc.edison.ui.components.LabelListItemForSelect
import com.umc.edison.ui.components.MiddleCancelButton
import com.umc.edison.ui.components.MiddleConfirmButton
import com.umc.edison.ui.theme.Gray800

@Composable
fun LabelSelectModalContent(
    labels: List<LabelModel>,
    onDismiss: () -> Unit,
    onConfirm: (List<LabelModel>) -> Unit,
    selectedLabels: List<LabelModel> = emptyList(),
    onAddLabelClicked: (() -> Unit)? = null,
    multiSelectMode: Boolean = false,
    updateToastMessage: (String) -> Unit = {},
) {
    val selected = remember { mutableStateOf(selectedLabels) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp)
    ) {
        Text(
            text = "라벨 선택",
            style = MaterialTheme.typography.displaySmall,
            color = Gray800,
            modifier = Modifier.padding(bottom = 18.dp)
        )

        if (onAddLabelClicked != null) {
            AddLabelButton(
                onClick = onAddLabelClicked,
            )
        }

        // 라벨 리스트
        LazyColumn(
            modifier = Modifier.height(450.dp)
        ) {
            items(labels.size) { index ->
                val label = labels[index]
                LabelListItemForSelect(
                    label = label,
                    selected = selected.value.map { it.id }.contains(label.id),
                    multiSelectMode = multiSelectMode,
                    onClick = {
                        if (multiSelectMode) {
                            if (selected.value.map { it.id }.contains(label.id)) {
                                selected.value = selected.value.filter { it.id != label.id }
                            } else if (selected.value.size >= 3) {
                                // 3개 이상 선택 불가
                                updateToastMessage("라벨은 최대 3개까지 선택 가능합니다.")
                            } else {
                                selected.value += label
                            }
                        } else {
                            if (selected.value.map { it.id }.contains(label.id)) {
                                selected.value = emptyList()
                            } else {
                                selected.value = listOf(label)
                            }
                        }
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 하단 버튼들
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 17.dp, end = 27.dp, bottom = 17.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MiddleCancelButton(
                text = "닫기",
                onClick = onDismiss,
                modifier = Modifier.weight(1f)
            )

            MiddleConfirmButton(
                text = "라벨 선택",
                onClick = {
                    onConfirm(selected.value)
                },
                enabled = true,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
