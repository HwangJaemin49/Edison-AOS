package com.umc.edison.ui.my_edison

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.umc.edison.ui.components.BubbleInput
import com.umc.edison.ui.theme.EdisonTheme

@Composable
fun MyEdisonScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        BubbleInput(
            onClick = { /* */}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BubbleSpaceScreenPreview() {
    EdisonTheme {
        MyEdisonScreen()
    }
}
