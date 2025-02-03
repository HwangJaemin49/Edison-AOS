package com.umc.edison.ui.my_edison

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.components.AddLabelButton
import com.umc.edison.ui.components.BasicFullButton
import com.umc.edison.ui.components.LabelListItemForSelect
import com.umc.edison.ui.theme.EdisonTheme
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000
import com.umc.edison.ui.theme.Yellow100
import com.umc.edison.ui.theme.Gray200
import com.umc.edison.ui.theme.Gray300

@Composable
fun LabelSelectModal(
    labels: List<LabelModel>,
    selectedLabels: List<LabelModel>,
    onConfirm: (List<LabelModel>) -> Unit ,
    onAddLabelClick: () -> Unit,
    onDismiss: () -> Unit
) {

    val context = LocalContext.current

    val checkBoxStates = remember {
        labels.map { label -> mutableStateOf(selectedLabels.contains(label)) }
    }

    labels.forEachIndexed { index, label ->
        if (selectedLabels.contains(label) && !checkBoxStates[index].value) {
            checkBoxStates[index].value = true
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White000)
            .padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
            .imePadding()
    ) {

        Text(
            text = "라벨 선택",
            style = MaterialTheme.typography.headlineLarge,
            color = Gray800,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        AddLabelButton {onAddLabelClick()}

        val selectedCount = checkBoxStates.count { it.value }


        labels.forEachIndexed { index, label ->

            LabelListItemForSelect(
                label = label,
                selected = checkBoxStates[index].value,
                multiSelectMode = true,
                onClick = {
                    if (checkBoxStates[index].value) {
                        checkBoxStates[index].value = false
                    } else if (selectedCount < 3) {
                        checkBoxStates[index].value = true
                    } else {
                        Toast.makeText(context, "최대 3개의 라벨만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }


        BasicFullButton(
            text = "선택 완료",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            enabled = checkBoxStates.indices.any { index ->
                checkBoxStates[index].value != selectedLabels.contains(labels[index])
            },
            onClick = {
                val updatedSelectedLabels = labels.filterIndexed { index, _ ->
                    checkBoxStates[index].value
                }
                onConfirm(updatedSelectedLabels)
                onDismiss()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLabelSelectModal() {

    EdisonTheme {
        LabelSelectModal(
            labels = listOf(
                LabelModel(id =1, name = "라벨1", color = Yellow100, bubbles = listOf()),
                LabelModel(id =2,name = "라벨2", color = Gray300, bubbles = listOf()),
                LabelModel(id =3,name = "라벨3", color = Gray200, bubbles = listOf())
            ),
            selectedLabels = listOf(
                LabelModel(id =1,name = "라벨1", color = Yellow100, bubbles = listOf()) // "라벨1"이 선택된 상태로 설정
                ),

            onConfirm = { selectedLabels ->
                println("Selected Labels: $selectedLabels")
            },
            onAddLabelClick = { // Add 버튼 클릭 시 처리
            },
            onDismiss = {
                println("Modal dismissed")
            }
        )
    }
}
