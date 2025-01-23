package com.umc.edison.ui.my_edison

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.NavHostController
import com.umc.edison.databinding.FragmentMyEdisonBinding

@Composable
fun MyEdisonScreen(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AndroidViewBinding(FragmentMyEdisonBinding::inflate) {
            titleTv.text = "Composable 함수에 AndroidViewBinding 사용하기"

            titleTv.setOnClickListener {
                titleTv.text = "클릭 리스너"
            }
        }
    }
}
