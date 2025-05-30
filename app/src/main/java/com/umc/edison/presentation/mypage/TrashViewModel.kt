package com.umc.edison.presentation.mypage

import com.umc.edison.domain.usecase.bubble.DeleteBubblesUseCase
import com.umc.edison.domain.usecase.bubble.GetAllTrashedBubblesUseCase
import com.umc.edison.domain.usecase.bubble.RecoverBubblesUseCase
import com.umc.edison.presentation.ToastManager
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
    toastManager: ToastManager,
    private val getAllTrashedBubblesUseCase: GetAllTrashedBubblesUseCase,
    private val recoverBubblesUseCase: RecoverBubblesUseCase,
    private val deleteBubblesUseCase: DeleteBubblesUseCase,
) : BaseViewModel(toastManager) {
    private val _uiState = MutableStateFlow(TrashState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun fetchDeletedBubbles() {
        collectDataResource(
            flow = getAllTrashedBubblesUseCase(),
            onSuccess = { bubbles ->
                bubbles.sortedByDescending { it.date }
                _uiState.update { it.copy(bubbles = bubbles.toPresentation()) }
            },
        )
    }

    fun updateBubbleRecoverMode(mode: BubbleRecoverMode) {
        _uiState.update { it.copy(mode = mode) }

        if (mode == BubbleRecoverMode.NONE) {
            clearSelection()
        }
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

    private fun clearSelection() {
        _uiState.update { it.copy(selectedBubbles = emptyList()) }
    }

    fun selectBubble(bubble: BubbleModel) {
        _uiState.update { it.copy(selectedBubbles = listOf(bubble)) }
    }

    fun deleteBubbles() {
        collectDataResource(
            flow = deleteBubblesUseCase(uiState.value.selectedBubbles.map { it.toDomain() }),
            onSuccess = {
                _uiState.update {
                    it.copy(
                        mode = BubbleRecoverMode.NONE,
                        bubbles = it.bubbles - it.selectedBubbles.toSet(),
                    )
                }
                showToast("버블이 삭제되었습니다.")
                fetchDeletedBubbles()
            },
        )
    }

    fun recoverBubbles() {
        collectDataResource(
            flow = recoverBubblesUseCase(uiState.value.selectedBubbles.map { it.toDomain() }),
            onSuccess = {
                _uiState.update {
                    it.copy(
                        mode = BubbleRecoverMode.NONE,
                        bubbles = it.bubbles - it.selectedBubbles.toSet(),
                    )
                }
                showToast("버블이 복원되었습니다.")
            },
        )
    }
}
