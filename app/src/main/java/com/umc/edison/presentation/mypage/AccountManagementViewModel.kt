package com.umc.edison.presentation.mypage

import android.util.Log
import com.umc.edison.domain.usecase.mypage.DeleteAccountUseCase
import com.umc.edison.domain.usecase.mypage.GetLogInStateUseCase
import com.umc.edison.domain.usecase.mypage.GetProfileInfoUseCase
import com.umc.edison.domain.usecase.mypage.LogOutUseCase
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
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
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
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    private fun fetchProfileInfo() {
        collectDataResource(
            flow = getProfileInfoUseCase(),
            onSuccess = { user ->
                _uiState.update { it.copy(user = user.toPresentation()) }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    fun updateMode(mode: AccountManagementMode) {
        _uiState.update { it.copy(mode = mode) }
    }

    fun updateEmail(email: String) {
        Log.d("AccountManagementViewModel", "updateEmail: $email")
    }

    fun logOut() {
        collectDataResource(
            flow = logOutUseCase(),
            onSuccess = {
                _uiState.update { it.copy(
                    isLoggedIn = false,
                    user = null,
                    toastMessage = "로그아웃 되었습니다."
                ) }
            },
            onError = { error ->
                _uiState.update { it.copy(
                    error = error,
                    toastMessage = "로그아웃에 실패했습니다."
                ) }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _uiState.update { it.copy(
                    isLoading = false,
                    mode = AccountManagementMode.NONE
                ) }
            }
        )
    }

    fun deleteAccount() {
        collectDataResource(
            flow = deleteAccountUseCase(),
            onSuccess = {
                _uiState.update { it.copy(
                    isLoggedIn = false,
                    user = null,
                    toastMessage = "회원 탈퇴 되었습니다."
                ) }
            },
            onError = { error ->
                _uiState.update { it.copy(
                    error = error,
                    toastMessage = "회원 탈퇴에 실패했습니다."
                ) }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _uiState.update { it.copy(
                    isLoading = false,
                    mode = AccountManagementMode.NONE
                ) }
            }
        )
    }

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}