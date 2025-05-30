package com.umc.edison.presentation.mypage

import com.umc.edison.domain.usecase.user.GetLogInStateUseCase
import com.umc.edison.domain.usecase.user.GetMyProfileInfoUseCase
import com.umc.edison.domain.usecase.user.LogOutUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AccountManagementViewModel @Inject constructor(
    private val getLogInStateUseCase: GetLogInStateUseCase,
    private val getMyProfileInfoUseCase: GetMyProfileInfoUseCase,
    private val logOutUseCase: LogOutUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(AccountManagementState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        fetchLoginState()
    }

    private fun fetchLoginState() {
        collectDataResource(
            flow = getLogInStateUseCase(),
            onSuccess = { isLoggedIn ->
                _uiState.update { it.copy(isLoggedIn = isLoggedIn) }

                if (isLoggedIn) {
                    fetchProfileInfo()
                }
            },
        )
    }

    private fun fetchProfileInfo() {
        collectDataResource(
            flow = getMyProfileInfoUseCase(),
            onSuccess = { user ->
                _uiState.update { it.copy(user = user.toPresentation()) }
            },
        )
    }

    fun updateMode(mode: AccountManagementMode) {
        _uiState.update { it.copy(mode = mode) }
    }

    fun logOut() {
        collectDataResource(
            flow = logOutUseCase(),
            onSuccess = {
                _baseState.update { it.copy(toastMessage = "로그아웃 되었습니다.") }
                _uiState.update { it.copy(isLoggedIn = false, user = null) }
            },
            onError = { _ ->
                _baseState.update { it.copy(toastMessage = "로그아웃에 실패했습니다.") }
            },
            onComplete = {
                _uiState.update {
                    it.copy(mode = AccountManagementMode.NONE)
                }
            }
        )
    }
}
