package com.umc.edison.presentation.base

data class BaseState(
    val isLoading: Boolean,
    val error: Throwable?,
) {
    companion object {
        val DEFAULT = BaseState(
            isLoading = false,
            error = null,
        )
    }
}
