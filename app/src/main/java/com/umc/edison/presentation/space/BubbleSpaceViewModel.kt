package com.umc.edison.presentation.space

import androidx.lifecycle.viewModelScope
import com.umc.edison.domain.usecase.mypage.GetLogInStateUseCase
import com.umc.edison.domain.usecase.sync.SyncDataUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.BubbleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BubbleSpaceViewModel @Inject constructor(
    private val syncDataUseCase: SyncDataUseCase,
    private val getLogInStateUseCase: GetLogInStateUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(BubbleSpaceState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        collectDataResource(
            flow = getLogInStateUseCase(),
            onSuccess = { isLoggedIn ->
                _uiState.update { it.copy(isLoggedIn = isLoggedIn) }
                syncBubbles()
            },
            onError = { error ->
                _uiState.update { it.copy(error = error, isLoading = false) }
            }
        )
    }

    private fun syncBubbles() {
        viewModelScope.launch {
            syncDataUseCase()
        }
    }

    fun updateBubbleSpaceMode(mode: BubbleSpaceMode) {
        _uiState.update { it.copy(mode = mode) }
    }

    fun updateSelectedTabIndex(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

    fun selectBubble(bubble: BubbleModel?) {
        _uiState.update { it.copy(selectedBubble = bubble) }
    }

    fun searchBubbles(query: String) {
        // 검색 USE CASE 호출
    }

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }

}