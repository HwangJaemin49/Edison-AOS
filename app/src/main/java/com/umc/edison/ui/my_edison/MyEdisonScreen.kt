package com.umc.edison.ui.my_edison

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.umc.edison.ui.components.BubbleInput
import com.umc.edison.ui.components.MyEdisonNavBar
import com.umc.edison.ui.navigation.NavRoute

@Composable
fun MyEdisonScreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BubbleInput(
            onClick = { /* */},
            onSwipeUp = {/* */}
        )
    }
}
