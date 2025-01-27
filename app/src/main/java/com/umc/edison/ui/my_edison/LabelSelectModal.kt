package com.umc.edison.ui.my_edison

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.ui.components.BasicFullButton
import com.umc.edison.ui.space.AddLabelButton
import com.umc.edison.ui.theme.EdisonTheme
import com.umc.edison.ui.theme.Gray800
import com.umc.edison.ui.theme.White000
import com.umc.edison.ui.theme.Yellow100
import com.umc.edison.ui.theme.Gray200
import com.umc.edison.ui.theme.Gray300

@Composable
fun LabelSelectModal(
    labels: List<LabelModel>,
    selectedLabels: List<LabelModel>,// 라벨 데이터 리스트
    onConfirm: (List<LabelModel>) -> Unit ,
    onDismiss: () -> Unit
    // 선택된 라벨 전달
) {
    val checkBoxStates = remember {
        labels.map { label -> mutableStateOf(selectedLabels.contains(label)) }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White000)
            .padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
            .imePadding()
    ) {

        // 헤더
        Text(
            text = "라벨 선택",
            style = MaterialTheme.typography.headlineLarge,
            color = Gray800,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        AddLabelButton {  }

        // 라벨 리스트
        labels.forEachIndexed { index, label ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(White000),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 라벨 색상 Box
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(color = label.color, shape = RoundedCornerShape(15.dp))
                )

                // 라벨 제목
                Text(
                    text = label.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Gray800,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )

                androidx.compose.material3.Checkbox(
                    checked = checkBoxStates[index].value, // 개별 상태 사용
                    onCheckedChange = {
                        checkBoxStates[index].value = it // 상태 저장
                        Log.d("LabelEditModal", "Checkbox $index is now $it") // 상태 로그 출력
                    },
                    colors = androidx.compose.material3.CheckboxDefaults.colors(
                        checkedColor =Color.Black,
                        uncheckedColor = Gray300
                    )
                )
            }
        }

        // 선택 완료 버튼
        BasicFullButton(
            text = "선택 완료",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            enabled = checkBoxStates.indices.any { index ->
                checkBoxStates[index].value != selectedLabels.contains(labels[index])
            }, // 하나라도 체크된 항목이 있으면 활성화
            onClick = {
                val updatedSelectedLabels = labels.filterIndexed { index, _ ->
                    checkBoxStates[index].value
                }
                onConfirm(updatedSelectedLabels) // 최종 선택된 라벨 전달
                onDismiss() // 모달 닫기
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLabelEditModal() {
    EdisonTheme {
        LabelSelectModal(
            labels = listOf(
                LabelModel(name = "라벨1", color = Yellow100),
                LabelModel(name = "라벨2", color = Gray300),
                LabelModel(name = "라벨3", color = Gray200)
            ),
            selectedLabels = listOf(
                LabelModel(name = "라벨1", color = Yellow100) // "라벨1"이 선택된 상태로 설정
                ),

            onConfirm = { selectedLabels ->
                println("Selected Labels: $selectedLabels")
            },
            onDismiss = {
                println("Modal dismissed")
            }
        )
    }
}
