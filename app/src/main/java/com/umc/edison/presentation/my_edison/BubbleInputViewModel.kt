package com.umc.edison.presentation.my_edison

import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.model.ContentType
import com.umc.edison.domain.usecase.bubble.AddBubblesUseCase
import com.umc.edison.domain.usecase.bubble.GetAllBubblesUseCase
import com.umc.edison.domain.usecase.bubble.GetBubbleUseCase
import com.umc.edison.domain.usecase.label.AddLabelUseCase
import com.umc.edison.domain.usecase.label.GetAllLabelsUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.label.LabelEditMode
import com.umc.edison.presentation.label.LabelListState
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.ContentBlockModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.presentation.model.toPresentation
import com.umc.edison.ui.my_edison.parseHtml
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BubbleInputViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAllLabelsUseCase: GetAllLabelsUseCase,
    private val getBubbleUseCase: GetBubbleUseCase,
    private val getAllBubblesUseCase: GetAllBubblesUseCase,
    private val addBubblesUseCase : AddBubblesUseCase,
    private val addLabelUseCase: AddLabelUseCase,
    ) : BaseViewModel() {

    private val _uiState = MutableStateFlow(BubbleInputState.DEFAULT)
    val uiState = _uiState.asStateFlow()


    init {
        fetchLabels()
        fetchBubbles()
        val id: Int = savedStateHandle["id"] ?: throw IllegalArgumentException("ID is required")
        fetchBubble(7)
        addTextBlock()

    }


    fun fetchBubble(bubbleId: Int) {

        _uiState.update { BubbleInputState.DEFAULT }

        collectDataResource(
            flow = getBubbleUseCase(bubbleId),
            onSuccess = { bubble ->
                _uiState.update { it.copy(bubble = bubble.toPresentation()) }
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



    private fun fetchBubbles() {
        collectDataResource(
            flow = getAllBubblesUseCase(),
            onSuccess = { bubbles ->
                println("도메인버블$bubbles")
                _uiState.update { it.copy(bubbles = bubbles.toPresentation()) }
                println("Fetched Bubble: ${bubbles.toPresentation()}")
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

    private fun fetchLabels() {
        collectDataResource(
            flow = getAllLabelsUseCase(),
            onSuccess = { labels ->
                _uiState.update { it.copy(labels = labels.toPresentation()) }
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

    fun updateLabel(newLabels: List<LabelModel>) {
        _uiState.value = _uiState.value.copy(
            bubble = _uiState.value.bubble.copy(
                labels = newLabels
            )
        )
    }

    fun confirmLabelModal(label: LabelModel) {
        if (uiState.value.labelEditMode == LabelEditMode.ADD) {


            collectDataResource(
                flow = addLabelUseCase(label.toDomain()),
                onSuccess = {
                    updateEditMode(LabelEditMode.NONE)
                    _uiState.update {
                        it.copy(
                            labelEditMode = LabelEditMode.NONE,
                            selectedLabel = LabelListState.DEFAULT.selectedLabel,
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
                    fetchLabels()
                }
            )
        }
    }

    fun updateEditMode(labelEditMode: LabelEditMode) {
        _uiState.update { it.copy(labelEditMode = labelEditMode) }
    }


    private fun addTextBlock() {

        val newTextBlock = ContentBlockModel(
            type = ContentType.TEXT,
            content = "",
            position = _uiState.value.bubble.contentBlocks.size,
        )

        val updatedContentBlocks = _uiState.value.bubble.contentBlocks + newTextBlock

        _uiState.value = _uiState.value.copy(
            bubble = _uiState.value.bubble.copy(
                contentBlocks = updatedContentBlocks
            )
        )

    }


    fun addContentBlock(imagePath: String) {

        val newImageBlock = ContentBlockModel(
            type = ContentType.IMAGE,
            content = imagePath,
            position = _uiState.value.bubble.contentBlocks.size,
        )

        val newTextBlock = ContentBlockModel(
            type = ContentType.TEXT,
            content = "",
            position = _uiState.value.bubble.contentBlocks.size + 1,
        )

        val updatedContentBlocks =
            _uiState.value.bubble.contentBlocks + newImageBlock + newTextBlock

        _uiState.value = _uiState.value.copy(
            bubble = _uiState.value.bubble.copy(
                contentBlocks = updatedContentBlocks
            )
        )

    }

    fun deleteContentBlock(contentBlock: ContentBlockModel) {
        val currentBubble = _uiState.value.bubble
        val contentBlocks = currentBubble.contentBlocks.toMutableList()
        println("delete " + contentBlocks)

        val targetIndex = contentBlocks.indexOfFirst { it.position == contentBlock.position }

        if (targetIndex == -1 || contentBlock.type != ContentType.IMAGE) return

        val nextTextBlockIndex = contentBlocks.indexOfFirst {
            it.position > contentBlock.position && it.type == ContentType.TEXT
        }

        val previousTextBlockIndex = contentBlocks.indexOfLast {
            it.position < contentBlock.position && it.type == ContentType.TEXT
        }

        if (nextTextBlockIndex != -1 && previousTextBlockIndex != -1) {
            val updatedTextBlock = contentBlocks[previousTextBlockIndex].copy(
                content = contentBlocks[previousTextBlockIndex].content + "\n" + contentBlocks[nextTextBlockIndex].content
            )

            contentBlocks[previousTextBlockIndex] = updatedTextBlock
            contentBlocks.removeAt(nextTextBlockIndex)
        }

        contentBlocks.removeAt(targetIndex)
        _uiState.update {
            it.copy(
                bubble = it.bubble.copy(contentBlocks = contentBlocks)

            )
        }
    }


    fun updateBubbleWithLink() {

        val previousBubble = _uiState.value.bubble

        if (previousBubble.id == 0 || previousBubble.contentBlocks.isNotEmpty()) {
            saveBubble()
        }

        val newBubbleState = BubbleInputState.DEFAULT.copy(
            bubble = BubbleModel(id = 0)
        )

        _uiState.update { newBubbleState }

        addTextBlock()


    }



    fun saveBubble() {

        println("Before Saving: ${_uiState.value.bubble}")

        val currentBubble = _uiState.value.bubble

        collectDataResource(
            flow = addBubblesUseCase(listOf(currentBubble.toDomain())),
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = null,
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
                fetchBubbles()
            }
        )
    }

    fun updateBubble(updatedBubble: BubbleModel) {
        _uiState.update { currentState ->
            currentState.copy(bubble = updatedBubble)
        }
    }
}