package com.umc.edison.presentation.mypage

import com.umc.edison.domain.usecase.mypage.GetDeletedBubblesUseCase
import com.umc.edison.domain.usecase.mypage.RecoverBubblesUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TrashViewModel @Inject constructor(
    private val getDeletedBubblesUseCase: GetDeletedBubblesUseCase,
    private val recoverBubblesUseCase: RecoverBubblesUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(TrashState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun fetchDeletedBubbles() {
        collectDataResource(
            flow = getDeletedBubblesUseCase(),
            onSuccess = { bubbles ->
                _uiState.update { it.copy(bubbles = bubbles.toPresentation()) }
            },
            onError = { error ->
                _uiState.update {
                    it.copy(
                        error = error,
                        toastMessage = error.message
                    )
                }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    fun updateBubbleRecoverMode(mode: BubbleRecoverMode) {
        _uiState.update { it.copy(mode = mode) }
    }

    fun toggleBubbleSelection(bubble: BubbleModel) {
        val selectedBubbles = if (uiState.value.selectedBubbles.contains(bubble)) {
            uiState.value.selectedBubbles - bubble
        } else {
            uiState.value.selectedBubbles + bubble
        }
        _uiState.update { it.copy(selectedBubbles = selectedBubbles) }
    }

    fun selectAllBubbles() {
        if (uiState.value.selectedBubbles.size == uiState.value.bubbles.size) {
            _uiState.update { it.copy(selectedBubbles = emptyList()) }
        } else {
            _uiState.update { it.copy(selectedBubbles = uiState.value.bubbles) }
        }
    }

    fun clearSelection() {
        _uiState.update { it.copy(selectedBubbles = emptyList()) }
    }

    fun selectBubble(bubble: BubbleModel) {
        _uiState.update { it.copy(selectedBubbles = listOf(bubble)) }
    }

    fun deleteBubbles() {
        // TODO: 영구 삭제 기능 구현
    }

    fun recoverBubbles() {
        collectDataResource(
            flow = recoverBubblesUseCase(uiState.value.selectedBubbles.map { it.toDomain() }),
            onSuccess = {
                _uiState.update {
                    it.copy(
                        toastMessage = "버블이 복원되었습니다.",
                        mode = BubbleRecoverMode.NONE,
                    )
                }
                fetchDeletedBubbles()
            },
            onError = { error ->
                _uiState.update {
                    it.copy(
                        error = error,
                        toastMessage = error.message
                    )
                }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    override fun clearError() {
        _uiState.update { it.copy(error = null, toastMessage = null) }
    }

}