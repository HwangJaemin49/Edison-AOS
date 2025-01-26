package com.umc.edison.presentation.label

import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.model.ContentType
import com.umc.edison.domain.usecase.bubble.AddBubblesUseCase
import com.umc.edison.domain.usecase.bubble.DeleteBubblesUseCase
import com.umc.edison.domain.usecase.label.GetAllLabelsUseCase
import com.umc.edison.domain.usecase.label.GetLabelDetailUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.ContentBlockModel
import com.umc.edison.presentation.model.BubbleModel
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
    private val addBubblesUseCase: AddBubblesUseCase,
    private val getAllLabelsUseCase: GetAllLabelsUseCase,
    private val deleteBubblesUseCase: DeleteBubblesUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(LabelDetailState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        val id: Int = savedStateHandle["id"] ?: throw IllegalArgumentException("ID is required")
        fetchBubbles(id)
    }

    private fun fetchBubbles(id: Int) {
        _uiState.update { LabelDetailState.DEFAULT }

        collectDataResource(
            flow = getLabelDetailUseCase(id),
            onSuccess = { label ->
                _uiState.update { it.copy(label = label.toPresentation()) }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _uiState.update { it.copy(isLoading = false) }
                if (_uiState.value.label.bubbles.isEmpty()) insertBubbles() // TODO: 추후에 수정해야 함 임시 더미데이터 삽입
            }
        )
    }

    fun updateEditMode(bubbleEditMode: BubbleEditMode) {
        if (bubbleEditMode == BubbleEditMode.NONE) {
            _uiState.update {
                it.copy(
                    bubbleEditMode = bubbleEditMode,
                    selectedBubbles = listOf()
                )
            }
        } else {
            _uiState.update { it.copy(bubbleEditMode = bubbleEditMode) }
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

    fun getMovableLabels() {
        collectDataResource(
            flow = getAllLabelsUseCase(),
            onSuccess = { allLabels ->
                val movableLabels = allLabels.toPresentation().filter { label ->
                    _uiState.value.selectedBubbles.any { bubble ->
                        bubble.labels.any { bubbleLabel ->
                            bubbleLabel.id != label.id
                        }
                    }
                }

                _uiState.update { it.copy(movableLabels = movableLabels) }
                updateEditMode(BubbleEditMode.MOVE)
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

    fun deleteSelectedBubbles() {
        collectDataResource(
            flow = deleteBubblesUseCase(_uiState.value.selectedBubbles.toSet().map { it.toDomain() }),
            onSuccess = {
                fetchBubbles(_uiState.value.label.id)
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

    // 임시 더미 데이터 추가 함수
    private fun insertBubbles() {
        val label = LabelModel(
            id = _uiState.value.label.id,
            name = _uiState.value.label.name,
            color = _uiState.value.label.color,
            bubbles = _uiState.value.label.bubbles
        )

        val dummyBubbles = listOf(
            BubbleModel(
                id = 1,
                contentBlocks = listOf(
                    ContentBlockModel(
                        content = "새우간장볶음 밥 앤나 명란젓",
                        type = ContentType.TEXT,
                        position = 0
                    ),
                ),
                mainImage = null,
                labels = listOf(label)
            ),
            BubbleModel(
                id = 2,
                contentBlocks = listOf(
                    ContentBlockModel(
                        content = "오늘 따뜻함 찾으러 나갔음. 생각보다 쉽게 찾았음. 따뜻함 한 스푼 더해서 커피 마셨는데 커피가 너무 맛있었음",
                        type = ContentType.TEXT,
                        position = 0
                    ),
                ),
                mainImage = null,
                labels = listOf(label)
            ),
            BubbleModel(
                id = 3,
                contentBlocks = listOf(
                    ContentBlockModel(
                        content = "길에서 고양이 마주치다가",
                        type = ContentType.TEXT,
                        position = 0
                    )
                ),
                mainImage = null,
                labels = listOf(label)
            ),
            BubbleModel(
                id = 4,
                contentBlocks = listOf(
                    ContentBlockModel(
                        content = "새우간장볶음 밥 앤나 명란젓",
                        type = ContentType.TEXT,
                        position = 0
                    ),
                ),
                mainImage = null,
                labels = listOf(label)
            ),
            BubbleModel(
                id = 5,
                contentBlocks = listOf(
                    ContentBlockModel(
                        content = "오늘 따뜻함 찾으러 나갔음. 생각보다 쉽게 찾았음. 따뜻함 한 스푼 더해서 커피 마셨는데 커피가 너무 맛있었음",
                        type = ContentType.TEXT,
                        position = 0
                    ),
                ),
                mainImage = null,
                labels = listOf(label)
            ),
            BubbleModel(
                id = 6,
                contentBlocks = listOf(
                    ContentBlockModel(
                        content = "길에서 고양이 마주치다가",
                        type = ContentType.TEXT,
                        position = 0
                    )
                ),
                mainImage = null,
                labels = listOf(label)
            )
        )

        collectDataResource(
            flow = addBubblesUseCase(dummyBubbles.map { it.toDomain() }),
            onSuccess = {
                _uiState.update {
                    it.copy(
                        label = it.label.copy(
                            bubbles = it.label.bubbles + dummyBubbles
                        )
                    )
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
}
