package com.umc.edison.presentation.mypage

import com.umc.edison.domain.usecase.mypage.GetLogInStateUseCase
import com.umc.edison.domain.usecase.mypage.GetProfileInfoUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AccountManagementViewModel @Inject constructor(
    private val getLogInStateUseCase: GetLogInStateUseCase,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
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
                _uiState.value = _uiState.value.copy(isLoggedIn = isLoggedIn)

                if (isLoggedIn) {
                    fetchProfileInfo()
                }
            },
            onError = { error ->
                _uiState.value = _uiState.value.copy(
                    error = error,
                    toastMessage = error.message
                )
            },
            onLoading = {
                _uiState.value = _uiState.value.copy(isLoading = true)
            },
            onComplete = {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        )
    }

    private fun fetchProfileInfo() {
        collectDataResource(
            flow = getProfileInfoUseCase(),
            onSuccess = { user ->
                _uiState.value = _uiState.value.copy(user = user.toPresentation())
            },
            onError = { error ->
                _uiState.value = _uiState.value.copy(
                    error = error,
                    toastMessage = error.message
                )
            }
        )
    }

    override fun clearError() {
        _uiState.value = _uiState.value.copy(error = null, toastMessage = null)
    }

}