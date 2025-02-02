package com.umc.edison.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.umc.edison.presentation.base.BaseState
import com.umc.edison.ui.theme.White000

@Composable
fun BaseContent(
    uiState: BaseState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White000)
    ) {
        if (uiState.isLoading) {
             LoadingScreen()
        } else {
            content()

            if (uiState.toastMessage != null) {
                 ToastScreen(
                    message = uiState.toastMessage!!,
                    onDismiss = { onDismiss() }
                )
            }
        }
    }
}