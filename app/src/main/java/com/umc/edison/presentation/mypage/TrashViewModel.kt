package com.umc.edison.presentation.mypage

import androidx.lifecycle.viewModelScope
import com.umc.edison.domain.usecase.bubble.DeleteBubblesUseCase
import com.umc.edison.domain.usecase.mypage.GetTrashedBubblesUseCase
import com.umc.edison.domain.usecase.mypage.RecoverBubblesUseCase
import com.umc.edison.domain.usecase.sync.SyncDataUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrashViewModel @Inject constructor(
    private val getTrashedBubblesUseCase: GetTrashedBubblesUseCase,
    private val recoverBubblesUseCase: RecoverBubblesUseCase,
    private val deleteBubblesUseCase: DeleteBubblesUseCase,
    private val syncDataUseCase: SyncDataUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(TrashState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun fetchDeletedBubbles() {
        collectDataResource(
            flow = getTrashedBubblesUseCase(),
            onSuccess = { bubbles ->
                bubbles.sortedByDescending { it.date }
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
                        toastMessage = "버블이 삭제되었습니다.",
                        mode = BubbleRecoverMode.NONE,
                        bubbles = it.bubbles - it.selectedBubbles.toSet(),
                    )
                }
                fetchDeletedBubbles()
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

    fun recoverBubbles() {
        collectDataResource(
            flow = recoverBubblesUseCase(uiState.value.selectedBubbles.map { it.toDomain() }),
            onSuccess = {
                _uiState.update {
                    it.copy(
                        toastMessage = "버블이 복원되었습니다.",
                        mode = BubbleRecoverMode.NONE,
                        bubbles = it.bubbles - it.selectedBubbles.toSet(),
                    )
                }
                syncData()
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

    private fun syncData() {
        viewModelScope.launch {
            try {
                syncDataUseCase()
            } catch (e: Throwable) {
                _uiState.update { it.copy(error = e) }
            }
        }
    }

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }

}