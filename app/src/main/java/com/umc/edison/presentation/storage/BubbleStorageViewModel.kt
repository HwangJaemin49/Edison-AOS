package com.umc.edison.presentation.storage

import com.umc.edison.domain.model.ContentType
import com.umc.edison.domain.usecase.bubble.AddBubblesUseCase
import com.umc.edison.domain.usecase.bubble.DeleteBubblesUseCase
import com.umc.edison.domain.usecase.bubble.GetAllBubblesUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.ContentBlockModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.presentation.model.toPresentation
import com.umc.edison.ui.theme.Aqua100
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BubbleStorageViewModel @Inject constructor(
    private val getAllBubblesUseCase: GetAllBubblesUseCase,
    private val addBubblesUseCase: AddBubblesUseCase,
    private val deleteBubblesUseCase: DeleteBubblesUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(BubbleStorageState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        fetchAllBubbles()
    }

    private fun fetchAllBubbles() {
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
                val someLabel =
                    LabelModel(0, "라벨1", Aqua100, bubbles = listOf())

                insertBubbles(label = someLabel)
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


    fun addBubbles(newBubbles: List<BubbleModel>) {
        collectDataResource(
            flow = addBubblesUseCase(newBubbles.map { it.toDomain() }),
            onSuccess = {
                _uiState.update { it.copy(bubbles = _uiState.value.bubbles + newBubbles) }
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

    fun deleteBubbles(bubblesToDelete: List<BubbleModel>) {
        collectDataResource(
            flow = deleteBubblesUseCase(bubblesToDelete.map { it.toDomain() }),
            onSuccess = {
                _uiState.update {
                    it.copy(bubbles = it.bubbles - bubblesToDelete)
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

    fun deleteSelectedBubbles(showBottomNav: (Boolean) -> Unit) {
        collectDataResource(
            flow = deleteBubblesUseCase(_uiState.value.selectedBubbles.toSet().map { it.toDomain() }),
            onSuccess = {
                updateEditMode(BubbleStorageMode.NONE)
                showBottomNav(true)
                fetchAllBubbles()
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

    private fun insertBubbles(label: LabelModel) {
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
                labels = listOf(label) // ✅ 여기에 label 추가
            ),
            BubbleModel(
                id = 2,
                contentBlocks = listOf(
                    ContentBlockModel(
                        content = "오늘 따뜻함 찾으러 나갔음. 생각보다 쉽게 찾았음.",
                        type = ContentType.TEXT,
                        position = 0
                    ),
                ),
                mainImage = null,
                labels = listOf(label) // ✅ 여기에 label 추가
            ),
        )

        collectDataResource(
            flow = addBubblesUseCase(dummyBubbles.map { it.toDomain() }),
            onSuccess = {
                _uiState.update {
                    it.copy(bubbles = it.bubbles + dummyBubbles)
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
