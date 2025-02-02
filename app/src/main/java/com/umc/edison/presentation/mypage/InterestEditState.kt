package com.umc.edison.presentation.mypage

import com.umc.edison.domain.model.InterestCategory
import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.InterestModel

data class InterestEditState(
    override val isLoading: Boolean,
    val interest: InterestModel,
    override val error: Throwable?,
    override val toastMessage: String?
) : BaseState {
    companion object {
        val DEFAULT = InterestEditState(
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
            error = null,
            toastMessage = null
        )
    }
}
