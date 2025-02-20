package com.umc.edison.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.umc.edison.presentation.base.BaseState
import com.umc.edison.ui.theme.White000

@Composable
fun BaseContent(
    uiState: BaseState,
    clearToastMessage: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = White000,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .background(containerColor)
    ) {
        if (topBar != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                topBar()
            }
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            if (uiState.isLoading) {
                LoadingScreen()
            } else {
                content()

                if (uiState.toastMessage != null) {
                    ToastScreen(
                        message = uiState.toastMessage!!,
                        onDismiss = { clearToastMessage() }
                    )
                }
            }
        }

        if (bottomBar != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                bottomBar()
            }
        }
    }
}