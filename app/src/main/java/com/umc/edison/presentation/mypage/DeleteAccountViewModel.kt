package com.umc.edison.presentation.mypage

import com.umc.edison.domain.usecase.mypage.DeleteAccountUseCase
import com.umc.edison.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(DeleteAccountState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun toggleAgree() {
        _uiState.update { it.copy(isAgree = !it.isAgree) }
    }

    fun deleteAccount() {
        collectDataResource(
            flow = deleteAccountUseCase(),
            onSuccess = {
                _uiState.update { it.copy(isDeleted = true) }
                _baseState.update { it.copy(toastMessage = "회원 탈퇴 되었습니다.") }
            },
            onError = { error ->
                _uiState.update { it.copy(isDeleted = false) }
                _baseState.update { it.copy(toastMessage = "회원 탈퇴에 실패했습니다.") }
            },
        )
    }
}
