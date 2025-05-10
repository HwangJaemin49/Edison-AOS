package com.umc.edison.presentation.base

data class BaseState(
    val isLoading: Boolean,
    val error: Throwable?,
    val toastMessage: String?
) {
    companion object {
        val DEFAULT = BaseState(
            isLoading = false,
            error = null,
            toastMessage = null
        )
    }
}
