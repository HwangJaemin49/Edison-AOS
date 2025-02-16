package com.umc.edison.presentation.login

import com.umc.edison.domain.model.IdentityCategory
import com.umc.edison.domain.model.InterestCategory
import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.IdentityModel
import com.umc.edison.presentation.model.InterestModel

data class IdentityTestState(
    override val isLoading: Boolean,
    override val error: Throwable? = null,
    val interest: InterestModel,
    val identity: IdentityModel,
    val selectedTabIndex: Int,
    override val toastMessage: String? = null

): BaseState {
    companion object {
        val DEFAULT = IdentityTestState(
            isLoading = false,
            interest = InterestModel(
                id = 0,
                question = InterestCategory.NONE.question,
                questionTip = InterestCategory.NONE.questionTip,
                descriptionFirst = InterestCategory.NONE.descriptionFirst,
                descriptionSecond = null,
                options = emptyList(),
                selectedKeywords = emptyList()
            ),
            identity = IdentityModel(
                id = IdentityCategory.NONE.ordinal,
                question = IdentityCategory.NONE.question,
                descriptionFirst = IdentityCategory.NONE.descriptionFirst,
                descriptionSecond = IdentityCategory.NONE.descriptionSecond,
                selectedKeywords = emptyList(),
                options = emptyList()
            ),
            error = null,
            selectedTabIndex = 0,
            toastMessage = null
        )

    }
}