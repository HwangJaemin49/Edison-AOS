package com.umc.edison.presentation.login

import com.umc.edison.presentation.model.IdentityModel

data class IdentityTestState(
    val identity: IdentityModel,
    val selectedTabIndex: Int,
) {
    companion object {
        val DEFAULT = IdentityTestState(
            identity = IdentityModel.DEFAULT,
            selectedTabIndex = 0,
        )
    }
}
