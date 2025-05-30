package com.umc.edison.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.umc.edison.presentation.ToastManager
import com.umc.edison.ui.components.ToastMessage
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun ToastScreen() {
    val toastManager = rememberToastManager()
    val toastMessage by toastManager.toastMessage.collectAsState()
    var visibleMessage by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()
    var clearJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            visibleMessage = toastMessage
            clearJob?.cancel()
            clearJob = coroutineScope.launch {
                delay(2000)
                visibleMessage = null
                toastManager.clearToast()
            }
        }
    }

    visibleMessage?.let {
        Popup(
            alignment = Alignment.Center,
            properties = PopupProperties(focusable = false)
        ) {
            ToastMessage(
                message = it,
                isVisible = true,
                onDismiss = {
                    visibleMessage = null
                    toastManager.clearToast()
                }
            )
        }
    }
}

@Composable
fun rememberToastManager(): ToastManager {
    val context = LocalContext.current
    val entry = remember(context) {
        EntryPointAccessors.fromApplication(context, ToastManagerEntryPoint::class.java)
    }
    return entry.toastManager()
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ToastManagerEntryPoint {
    fun toastManager(): ToastManager
}
