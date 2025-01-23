package com.umc.edison.ui.my_edison

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.umc.edison.presentation.my_edison.BubbleInputViewModel

@Composable
fun BubbleInputScreen(
    viewModel: BubbleInputViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 뒤로가기 버튼, 더보기 버튼, 체크 버튼 (ROW)

        // 버블 도어 불러오기
//        BubbleDoor(
//            bubble = BubbleModel
//        ) { }

    }
}