package com.umc.edison.ui.my_edison

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.umc.edison.ui.components.BubbleInput
import com.umc.edison.ui.components.BubbleStorageTopBar
import com.umc.edison.ui.theme.EdisonTheme

@Composable
fun MyEdisonScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center // 중앙 정렬
        ) {
            BubbleInput(
                onClick = {  navController.navigate("bubble_input_screen") },
                onSwipeUp = { navController.navigate("bubble_input_screen") }
            )
        }

        BubbleStorageTopBar(
            onProfileClicked = { /* */ },
            onCompassClicked = { /* */ }
        )
    }
}
