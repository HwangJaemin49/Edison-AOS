package com.umc.edison.presentation.storage

import com.umc.edison.domain.model.ContentType
import com.umc.edison.domain.usecase.bubble.AddBubblesUseCase
import com.umc.edison.domain.usecase.bubble.SoftDeleteBubblesUseCase
import com.umc.edison.domain.usecase.bubble.GetAllBubblesUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.ContentBlockModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BubbleStorageViewModel @Inject constructor(
    private val getAllBubblesUseCase: GetAllBubblesUseCase,
    private val addBubblesUseCase: AddBubblesUseCase,
    private val softDeleteBubblesUseCase: SoftDeleteBubblesUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(BubbleStorageState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        fetchAllBubbles()
    }

    private fun fetchAllBubbles() { // TODO: 사용하는 비즈니스 로직이 7일간의 버블만 갖고오는 로직이라 이 부분은 UI 구현이 끝난 이후에 수정
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
                if (_uiState.value.bubbles.isEmpty()) insertBubbles()
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

    private fun insertBubbles() {
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
                labels = emptyList()
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
                labels = emptyList()
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

    override fun clearError() {
        TODO("Not yet implemented")
    }

}