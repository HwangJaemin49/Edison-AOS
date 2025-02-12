package com.umc.edison.presentation.login

import android.content.Context
import androidx.navigation.NavHostController
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.ui.navigation.NavRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleLoginHelper: GoogleLoginHelper
): BaseViewModel() {
    private val _uiState = MutableStateFlow(LoginState.DEFAULT)
    val uiState = _uiState.asStateFlow()


    fun signInWithGoogle(context: Context, navController: NavHostController) {
        googleLoginHelper.signInWithGoogle(
            context = context,
            onSuccess = { user->
                _uiState.update { it.copy(toastMessage = "로그인 성공!", user = user) }
                navController.navigate(NavRoute.MakeNickName.route)
            },
            onFailure = { message ->
                _uiState.update { it.copy(toastMessage = message) }
            },
            onLoading = { isLoading ->
                _uiState.update { it.copy(isLoading = isLoading) }
            }
        )
    }
    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}