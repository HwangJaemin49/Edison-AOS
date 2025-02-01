package com.umc.edison.presentation.base

interface BaseState {
    val isLoading: Boolean
    val error: Throwable?
    val toastMessage: String?
}
