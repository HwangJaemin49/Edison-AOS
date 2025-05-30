package com.umc.edison.presentation.login

import androidx.navigation.NavHostController
import com.umc.edison.domain.usecase.user.GetMyProfileInfoUseCase
import com.umc.edison.domain.usecase.user.UpdateProfileInfoUseCase
import com.umc.edison.presentation.ToastManager
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MakeNickNameViewModel @Inject constructor(
    toastManager: ToastManager,
    private val getMyProfileInfoUseCase: GetMyProfileInfoUseCase,
    private val updateProfileInfoUseCase: UpdateProfileInfoUseCase,
) : BaseViewModel(toastManager) {
    private val _uiState = MutableStateFlow(MakeNickNameState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        fetchProfileInfo()
    }

    private fun fetchProfileInfo() {
        collectDataResource(
            flow = getMyProfileInfoUseCase(),
            onSuccess = { user ->
                _uiState.update { it.copy(user = user.toPresentation()) }
            },
        )
    }

    // TODO: Implement nickname validation logic
    fun makeNickName(nickname: String, navController: NavHostController) {
//        collectDataResource(
//            flow = updateProfileInfoUseCase(
//
//            ),
//            onSuccess = {
//                _uiState.update { it.copy(user = it.user.copy(nickname = nickname)) }
//                CoroutineScope(Dispatchers.Main).launch {
//                    navController.navigate(NavRoute.IdentityTest.route)
//                }
//            },
//        )
    }
}
