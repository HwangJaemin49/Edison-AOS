package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.model.InterestModel

data class InterestEditState(
    val interest: InterestModel,
    val isEdited: Boolean
) {
    companion object {
        val DEFAULT = InterestEditState(
            interest = InterestModel.DEFAULT,
            isEdited = false
        )
    }
}
