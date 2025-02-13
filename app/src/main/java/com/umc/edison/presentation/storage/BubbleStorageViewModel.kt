package com.umc.edison.presentation.storage

import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.usecase.bubble.GetAllBubblesUseCase
import com.umc.edison.domain.usecase.bubble.SoftDeleteBubblesUseCase
import com.umc.edison.domain.usecase.bubble.MoveBubblesUseCase
import com.umc.edison.domain.usecase.label.GetAllLabelsUseCase
import com.umc.edison.domain.usecase.label.GetLabelDetailUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BubbleStorageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLabelDetailUseCase: GetLabelDetailUseCase,
    private val getAllLabelsUseCase: GetAllLabelsUseCase,
    private val getAllBubblesUseCase: GetAllBubblesUseCase,
    private val softDeleteBubblesUseCase: SoftDeleteBubblesUseCase,
    private val moveBubblesUseCase: MoveBubblesUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(BubbleStorageState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        val id: Int? = savedStateHandle["id"]

        if (id != null) {
            fetchLabelDetail(id)
        } else {
            fetchAllBubbles()
        }
    }

    fun fetchLabelDetail(id: Int) {
        _uiState.update { BubbleStorageState.DEFAULT }

        collectDataResource(
            flow = getLabelDetailUseCase(id),
            onSuccess = { label ->
                val shuffledBubbles = label.bubbles.shuffled().toPresentation()
                _uiState.update {
                    it.copy(
                        label = label.toPresentation().copy(bubbles = shuffledBubbles)
                    )
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
                _uiState.update { it.copy(isLoading = false, labelId = id) }
            }
        )
    }

    fun fetchAllBubbles() { // TODO: 사용하는 비즈니스 로직이 7일간의 버블만 갖고오는 로직이라 이 부분은 UI 구현이 끝난 이후에 수정
        collectDataResource(
            flow = getAllBubblesUseCase(),
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

    fun updateEditMode(mode: BubbleStorageMode) {
        if (mode == BubbleStorageMode.NONE) {
            _uiState.update {
                it.copy(
                    bubbleStorageMode = mode,
                    selectedBubbles = emptyList()
                )
            }
        } else {
            _uiState.update { it.copy(bubbleStorageMode = mode) }
        }
    }

    fun selectBubble(bubble: BubbleModel) {
        _uiState.update {
            it.copy(
                selectedBubbles = listOf(bubble)
            )
        }
    }

    fun toggleSelectBubble(bubble: BubbleModel) {
        _uiState.update {
            if (it.selectedBubbles.contains(bubble)) {
                it.copy(
                    selectedBubbles = it.selectedBubbles - bubble
                )
            } else {
                it.copy(
                    selectedBubbles = it.selectedBubbles + bubble
                )
            }
        }
    }

    fun deleteSelectedBubbles(showBottomNav: (Boolean) -> Unit) {
        collectDataResource(
            flow = softDeleteBubblesUseCase(_uiState.value.selectedBubbles.toSet().map { it.toDomain() }),
            onSuccess = {
                updateEditMode(BubbleStorageMode.NONE)
                showBottomNav(true)

                _uiState.value.label?.let { it1 -> fetchLabelDetail(it1.id) }

                if (_uiState.value.label == null) {
                    fetchAllBubbles()
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

    fun getMovableLabels() {
        collectDataResource(
            flow = getAllLabelsUseCase(),
            onSuccess = { allLabels ->
                val movableLabels = allLabels.toPresentation().filter { label ->
                    _uiState.value.label?.id != label.id
                }

                _uiState.update { it.copy(movableLabels = movableLabels) }
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

    fun moveSelectedBubbles(label: LabelModel, showBottomNav: (Boolean) -> Unit) {
        if (_uiState.value.label == null) return

        collectDataResource(
            flow = moveBubblesUseCase(
                bubbles = _uiState.value.selectedBubbles.toSet().map { it.toDomain() },
                moveFrom = _uiState.value.label!!.toDomain(),
                moveTo = label.toDomain()
            ),
            onSuccess = {
                updateEditMode(BubbleStorageMode.NONE)
                showBottomNav(true)
                _uiState.value.label?.let { it1 -> fetchLabelDetail(it1.id) }
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

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }

}