package com.umc.edison.presentation.mypage

import com.umc.edison.presentation.model.IdentityModel

data class IdentityEditState(
    val identity: IdentityModel,
    val isEdited: Boolean
) {
    companion object {
        val DEFAULT = IdentityEditState(
            identity = IdentityModel.DEFAULT,
            isEdited = false
        )
    }
}
