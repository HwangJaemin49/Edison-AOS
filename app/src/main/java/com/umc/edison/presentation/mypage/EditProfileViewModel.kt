package com.umc.edison.presentation.mypage

import com.umc.edison.domain.usecase.mypage.GetProfileInfoUseCase
import com.umc.edison.domain.usecase.mypage.UpdateUserProfileUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(EditProfileState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        fetchProfileInfo()
    }

    private fun fetchProfileInfo() {
        collectDataResource(
            flow = getProfileInfoUseCase(),
            onSuccess = { user ->
                _uiState.update { it.copy(user = user.toPresentation()) }
            },
        )
    }

    fun updateUserProfile() {
        collectDataResource(
            flow = updateUserProfileUseCase(_uiState.value.user.toDomain()),
            onSuccess = {
                _baseState.update { it.copy(toastMessage = "프로필이 수정되었습니다.") }
            },
        )
    }

    fun updateUserNickname(nickname: String) {
        _uiState.update { it.copy(user = it.user.copy(nickname = nickname)) }
    }

    fun updateUserProfileImage(profileImage: String) {
        _uiState.update { it.copy(user = it.user.copy(profileImage = profileImage)) }
    }
}
