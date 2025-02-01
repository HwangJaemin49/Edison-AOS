package com.umc.edison.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}