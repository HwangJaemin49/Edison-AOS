package com.umc.edison.presentation.edison

import com.umc.edison.domain.usecase.bubble.GetAllBubblesUseCase
import com.umc.edison.domain.usecase.bubble.GetSearchBubblesUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MyEdisonViewModel @Inject constructor(
    private val getAllBubblesUseCase: GetAllBubblesUseCase,
    private val getSearchBubblesUseCase: GetSearchBubblesUseCase,
) : BaseViewModel() {
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

    fun fetchSearchBubbles(query: String) {
        collectDataResource(
            flow = getSearchBubblesUseCase(query),
            onSuccess = { bubbles ->
                _uiState.update { it.copy(searchResults = bubbles.toPresentation()) }

                if (bubbles.isEmpty()) {
                    _uiState.update {
                        it.copy(toastMessage = "검색 결과를 찾을 수 없습니다.")
                    }
                }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true, query = query) }
            },
            onComplete = {
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}