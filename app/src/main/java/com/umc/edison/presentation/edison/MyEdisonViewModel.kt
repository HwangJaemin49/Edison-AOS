package com.umc.edison.presentation.edison

import com.umc.edison.domain.usecase.bubble.GetAllBubblesUseCase
import com.umc.edison.domain.usecase.bubble.SearchBubblesUseCase
import com.umc.edison.presentation.ToastManager
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyEdisonViewModel @Inject constructor(
    toastManager: ToastManager,
    private val getAllBubblesUseCase: GetAllBubblesUseCase,
    private val searchBubblesUseCase: SearchBubblesUseCase,
) : BaseViewModel(toastManager) {
    private val _uiState = MutableStateFlow(MyEdisonState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun fetchBubbles() {
        collectDataResource(
            flow = getAllBubblesUseCase(),
            onSuccess = { bubbles ->
                _uiState.update {
                    it.copy(bubbles = bubbles.toPresentation().filter { bubble ->
                        bubble.id != _uiState.value.bubble.id
                    })
                }
            },
        )
    }

    fun fetchSearchBubbles(query: String) {
        collectDataResource(
            flow = searchBubblesUseCase(query),
            onSuccess = { bubbles ->
                _uiState.update { it.copy(searchResults = bubbles.toPresentation()) }

                if (bubbles.isEmpty()) {
                    showToast("검색 결과를 찾을 수 없습니다.")
                }
            },
            onLoading = {
                _uiState.update { it.copy(query = query) }
            },
        )
    }

    fun resetSearchResults() {
        _uiState.update { it.copy(searchResults = emptyList(), query = "") }
    }
}
