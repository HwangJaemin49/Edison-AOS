package com.umc.edison.presentation.label

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.usecase.bubble.MoveBubblesUseCase
import com.umc.edison.domain.usecase.bubble.SoftDeleteBubblesUseCase
import com.umc.edison.domain.usecase.label.GetAllLabelsUseCase
import com.umc.edison.domain.usecase.label.GetLabelDetailUseCase
import com.umc.edison.presentation.baseBubble.BaseBubbleViewModel
import com.umc.edison.presentation.baseBubble.LabelDetailMode
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LabelDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLabelDetailUseCase: GetLabelDetailUseCase,
    private val moveBubblesUseCase: MoveBubblesUseCase,
    private val getAllLabelsUseCase: GetAllLabelsUseCase,
    override val softDeleteBubblesUseCase: SoftDeleteBubblesUseCase,
) : BaseBubbleViewModel<LabelDetailMode, LabelDetailState>() {
    override val _uiState = MutableStateFlow(LabelDetailState.DEFAULT)
    override val uiState = _uiState.asStateFlow()

    init {
        val id: Int = savedStateHandle["id"] ?: throw IllegalArgumentException("ID is required")
        Log.i("LabelDetailViewModel", "labelId: $id")
        fetchLabelDetail(id)
    }

    fun fetchLabelDetail(id: Int) {
        _uiState.update { LabelDetailState.DEFAULT }

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
        )
    }

    fun getMovableLabels() {
        collectDataResource(
            flow = getAllLabelsUseCase(),
            onSuccess = { allLabels ->
                val movableLabels = allLabels.toPresentation().filter { label ->
                    _uiState.value.label.id != label.id && label.id != 0
                }

                _uiState.update { it.copy(movableLabels = movableLabels) }
            },
        )
    }

    fun moveSelectedBubbles(label: LabelModel, showBottomNav: (Boolean) -> Unit) {
        collectDataResource(
            flow = moveBubblesUseCase(
                bubbles = _uiState.value.selectedBubbles.toSet().map { it.toDomain() },
                moveFrom = _uiState.value.label.toDomain(),
                moveTo = label.toDomain()
            ),
            onSuccess = {
                updateEditMode(LabelDetailMode.NONE)
                showBottomNav(true)
                fetchLabelDetail(label.id)
            },
        )
    }

    override fun refreshDataAfterDeletion() {
        fetchLabelDetail(_uiState.value.label.id)
    }
}
