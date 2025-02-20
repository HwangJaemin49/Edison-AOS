package com.umc.edison.presentation.space

import com.umc.edison.domain.usecase.bubble.GetSearchBubblesUseCase
import com.umc.edison.domain.usecase.mypage.GetLogInStateUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BubbleSpaceViewModel @Inject constructor(
    private val getLogInStateUseCase: GetLogInStateUseCase,
    private val getSearchBubblesUseCase: GetSearchBubblesUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(BubbleSpaceState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun checkLoginState() {
        collectDataResource(
            flow = getLogInStateUseCase(),
            onSuccess = { isLoggedIn ->
                _uiState.update { it.copy(isLoggedIn = isLoggedIn) }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error, isLoading = false) }
            }
        )
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

    fun searchBubbles() {
        if (_uiState.value.query.isEmpty()) {
            _uiState.update { it.copy(searchResults = emptyList()) }
            return
        }

        collectDataResource(
            flow = getSearchBubblesUseCase(_uiState.value.query),
            onSuccess = { bubbles ->
                _uiState.update { it.copy(searchResults = bubbles.toPresentation()) }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                if (uiState.value.searchResults.isEmpty()) {
                    _uiState.update { it.copy(toastMessage = "검색 결과를 찾을 수 없습니다.") }
                }
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }

        if (query.isEmpty()) {
            _uiState.update { it.copy(searchResults = emptyList()) }
        }
    }

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }

}