package com.umc.edison.presentation.mypage

data class DeleteAccountState(
    val isAgree: Boolean,
    val isDeleted: Boolean,
) {
    companion object {
        val DEFAULT = DeleteAccountState(
            isAgree = false,
            isDeleted = false
        )
    }
}
