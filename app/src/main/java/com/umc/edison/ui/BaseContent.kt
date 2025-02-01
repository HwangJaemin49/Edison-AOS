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

            uiState.error?.let {
                 ErrorScreen(
                        message = uiState.errorMessage ?: it.message ?: "알 수 없는 오류가 발생했습니다.",
                        onDismiss = { onDismiss() }
                 )
            }
        }
    }
}