package com.umc.edison.presentation.login

import com.umc.edison.presentation.model.IdentityModel
import com.umc.edison.presentation.model.InterestModel

data class IdentityTestState(
    val interest: InterestModel,
    val identity: IdentityModel,
    val selectedTabIndex: Int,
) {
    companion object {
        val DEFAULT = IdentityTestState(
            interest = InterestModel.DEFAULT,
            identity = IdentityModel.DEFAULT,
            selectedTabIndex = 0,
        )
    }
}
