package com.umc.edison.presentation.login

import androidx.navigation.NavHostController
import com.umc.edison.domain.usecase.login.MakeNickNameUseCase
import com.umc.edison.domain.usecase.mypage.GetProfileInfoUseCase
import com.umc.edison.domain.usecase.mypage.UpdateUserProfileUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import com.umc.edison.ui.navigation.NavRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MakeNickNameViewModel @Inject constructor (
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val makeNickNameUseCase: MakeNickNameUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
): BaseViewModel() {

    private val _uiState = MutableStateFlow( MakeNickNameState .DEFAULT)
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

    fun makeNickName(nickname: String,navController: NavHostController) {
        collectDataResource(
            flow=makeNickNameUseCase(nickname),
            onSuccess = {
                _uiState.update { it.copy(user = it.user.copy(nickname = nickname)) }
                updateUserProfile(navController)
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

    private fun updateUserProfile(navController: NavHostController) {

        collectDataResource(
            flow = updateUserProfileUseCase(_uiState.value.user.toDomain()),
            onSuccess = {
                _uiState.update { it.copy(toastMessage = "닉네임이 설정되었습니다.") }
                buttonClicked(navController)
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

    private fun buttonClicked(navController: NavHostController) {
        navController.navigate(NavRoute.IdentityTest.route)
    }


    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}