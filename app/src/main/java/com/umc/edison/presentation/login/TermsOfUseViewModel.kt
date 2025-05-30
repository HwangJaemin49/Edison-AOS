package com.umc.edison.presentation.login

import androidx.navigation.NavHostController
import com.umc.edison.domain.usecase.user.GetMyProfileInfoUseCase
import com.umc.edison.presentation.ToastManager
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import com.umc.edison.ui.navigation.NavRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TermsOfUseViewModel @Inject constructor(
    toastManager: ToastManager,
    private val getMyProfileInfoUseCase: GetMyProfileInfoUseCase
) : BaseViewModel(toastManager) {
    private val _uiState = MutableStateFlow(TermsOfUseState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        checkUserLoginStatus()
    }

    private fun checkUserLoginStatus() {
        collectDataResource(
            flow = getMyProfileInfoUseCase(),
            onSuccess = { user ->
                _uiState.update { it.copy(user = user.toPresentation()) }
            },
        )
    }

    fun buttonClicked(navController: NavHostController) {
        val isLoggedIn = uiState.value.user.email.isNotEmpty()

        if (isLoggedIn) {
            navController.navigate(NavRoute.MakeNickName.route) {
                popUpTo(NavRoute.TermsOfUse.route) { inclusive = true }
            }
        } else {
            navController.navigate(NavRoute.MyEdison.route) {
                popUpTo(NavRoute.TermsOfUse.route) { inclusive = true }
            }
        }
    }
}
