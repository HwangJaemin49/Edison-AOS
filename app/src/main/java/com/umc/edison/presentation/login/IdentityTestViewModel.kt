package com.umc.edison.presentation.login

import com.umc.edison.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class IdentityTestViewModel @Inject constructor (
): BaseViewModel(

) {

    private val _uiState = MutableStateFlow(IdentityTestState.DEFAULT)
    val uiState = _uiState.asStateFlow()



    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}