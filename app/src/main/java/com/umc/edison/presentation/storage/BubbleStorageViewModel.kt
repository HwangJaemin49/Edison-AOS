package com.umc.edison.presentation.storage

import com.umc.edison.domain.usecase.bubble.SoftDeleteBubblesUseCase
import com.umc.edison.domain.usecase.bubble.GetSearchBubblesUseCase
import com.umc.edison.domain.usecase.bubble.GetStorageBubbleUseCase
import com.umc.edison.presentation.baseBubble.BaseBubbleViewModel
import com.umc.edison.presentation.baseBubble.BubbleStorageMode
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BubbleStorageViewModel @Inject constructor(
    private val getStorageBubbleUseCase: GetStorageBubbleUseCase,
    private val getSearchBubblesUseCase: GetSearchBubblesUseCase,
    override val softDeleteBubblesUseCase: SoftDeleteBubblesUseCase,
) : BaseBubbleViewModel<BubbleStorageMode, BubbleStorageState>() {

    override val _uiState = MutableStateFlow(BubbleStorageState.DEFAULT)
    override val uiState = _uiState.asStateFlow()

    fun fetchStorageBubbles() {
        collectDataResource(
            flow = getStorageBubbleUseCase(),
            onSuccess = { bubbles ->
                _uiState.update { it.copy(bubbles = bubbles.toPresentation()) }
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
                _uiState.update { it.copy(bubbles = bubbles.toPresentation()) }
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

    override fun refreshDataAfterDeletion() {
        fetchStorageBubbles()
    }
}