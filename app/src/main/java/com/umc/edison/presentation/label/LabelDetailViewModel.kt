package com.umc.edison.presentation.label

import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.model.ContentType
import com.umc.edison.domain.usecase.label.GetLabelDetailUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.ui.theme.Yellow100
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LabelDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLabelDetailUseCase: GetLabelDetailUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(LabelDetailState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        val id: Int = savedStateHandle["id"] ?: throw IllegalArgumentException("ID is required")
        // fetchBubbles(id) // 실제 데이터 호출 대신 주석 처리
        insertBubbles(id) // 임시 더미 데이터 추가
    }

    // 기존 fetchBubbles 함수
    private fun fetchBubbles(id: Int) {
        collectDataResource(
            flow = getLabelDetailUseCase(id),
            onSuccess = { label ->
                _uiState.update { it.copy(label = label.toLabelDetailPresentation()) }
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
    private fun insertBubbles(id: Int) {
        val dummyBubbles = listOf(
            BubbleModel(
                id = 1,
                contentBlocks = listOf(
                    BubbleModel.BubbleContentBlock(content = "새우간장볶음 밥 앤나 명란젓", type = ContentType.TEXT, position = 0),
                ),
                mainImage = null,
                labels = listOf(BubbleModel.LabelModel(id = 1, name = "음식", color = Yellow100))
            ),
            BubbleModel(
                id = 2,
                contentBlocks = listOf(
                    BubbleModel.BubbleContentBlock(content = "오늘 따뜻함 찾으러 나갔음. 생각보다 쉽게 찾았음. 따뜻함 한 스푼 더해서 커피 마셨는데 커피가 너무 맛있었음", type = ContentType.TEXT, position = 0),
                ),
                mainImage = null,
                labels = listOf(BubbleModel.LabelModel(id = 2, name = "일상", color = Yellow100))
            ),
            BubbleModel(
                id = 3,
                contentBlocks = listOf(
                    BubbleModel.BubbleContentBlock(
                        content = "길에서 고양이 마주치다가",
                        type = ContentType.TEXT,
                        position = 0
                    )
                ),
                mainImage = null,
                labels = listOf(BubbleModel.LabelModel(id = 3, name = "동물", color = Yellow100))
            ),
            BubbleModel(
                id = 4,
                contentBlocks = listOf(
                    BubbleModel.BubbleContentBlock(content = "새우간장볶음 밥 앤나 명란젓", type = ContentType.TEXT, position = 0),
                ),
                mainImage = null,
                labels = listOf(BubbleModel.LabelModel(id = 1, name = "음식", color = Yellow100))
            ),
            BubbleModel(
                id = 5,
                contentBlocks = listOf(
                    BubbleModel.BubbleContentBlock(content = "오늘 따뜻함 찾으러 나갔음. 생각보다 쉽게 찾았음. 따뜻함 한 스푼 더해서 커피 마셨는데 커피가 너무 맛있었음", type = ContentType.TEXT, position = 0),
                ),
                mainImage = null,
                labels = listOf(BubbleModel.LabelModel(id = 2, name = "일상", color = Yellow100))
            ),
            BubbleModel(
                id = 6,
                contentBlocks = listOf(
                    BubbleModel.BubbleContentBlock(
                        content = "길에서 고양이 마주치다가",
                        type = ContentType.TEXT,
                        position = 0
                    )
                ),
                mainImage = null,
                labels = listOf(BubbleModel.LabelModel(id = 3, name = "동물", color = Yellow100))
            )
        )

        _uiState.update {
            it.copy(
                label = it.label.copy(
                    id = id,
                    labelName = "더미 라벨",
                    labelColor = Yellow100,
                    bubbles = dummyBubbles
                )
            )
        }
    }
}
