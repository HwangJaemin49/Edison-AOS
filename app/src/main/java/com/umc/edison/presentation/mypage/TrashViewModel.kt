package com.umc.edison.presentation.mypage

import com.umc.edison.domain.usecase.mypage.GetDeletedBubblesUseCase
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
    private val getDeletedBubblesUseCase: GetDeletedBubblesUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(TrashState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun fetchDeletedBubbles() {
        collectDataResource(
            flow = getDeletedBubblesUseCase(),
            onSuccess = { bubbles ->
                if (bubbles.isEmpty()) {
                    val tempBubbles = getTempBubbles()
                    _uiState.update { it.copy(bubbles = tempBubbles) }
                } else {
                    _uiState.update { it.copy(bubbles = bubbles.toPresentation()) }
                }
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
        _uiState.update { it.copy(selectedBubbles = uiState.value.bubbles) }
    }

    fun deleteBubbles() {

    }

    fun recoverBubbles() {

    }

    private fun getTempBubbles(): List<BubbleModel> {
        // 10개의 임시 버블 생성
        return List(20) {
            BubbleModel(
                id = it,
                title = "임시 버블 $it",
                contentBlocks = emptyList(),
            )
        }
    }

    override fun clearError() {
        _uiState.update { it.copy(error = null, toastMessage = null) }
    }

}