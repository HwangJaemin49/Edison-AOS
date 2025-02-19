package com.umc.edison.presentation.mypage

import com.umc.edison.domain.model.IdentityCategory
import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.IdentityModel

data class IdentityEditState(
    override val isLoading: Boolean,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
    val identity: IdentityModel,
    val isEdited: Boolean = false
) : BaseState {
    companion object {
        val DEFAULT = IdentityEditState(
            isLoading = false,
            identity = IdentityModel(
                id = IdentityCategory.NONE.ordinal,
                question = IdentityCategory.NONE.question,
                descriptionFirst = IdentityCategory.NONE.descriptionFirst,
                descriptionSecond = IdentityCategory.NONE.descriptionSecond,
                selectedKeywords = emptyList(),
                options = emptyList()
            )
        )
    }
}
