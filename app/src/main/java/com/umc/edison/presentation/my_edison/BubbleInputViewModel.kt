package com.umc.edison.presentation.my_edison

import com.umc.edison.domain.model.ContentType
import com.umc.edison.domain.usecase.label.AddLabelUseCase
import com.umc.edison.domain.usecase.label.DeleteLabelUseCase
import com.umc.edison.domain.usecase.label.GetAllLabelsUseCase
import com.umc.edison.domain.usecase.label.UpdateLabelUseCase
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
class BubbleInputViewModel @Inject constructor(
    private val getAllLabelsUseCase: GetAllLabelsUseCase,
    private val addLabelsUseCase: AddLabelUseCase,
    ) : BaseViewModel() {

    private val _uiState = MutableStateFlow(BubbleInputState.DEFAULT)
    val uiState = _uiState.asStateFlow()



    init {
        addTextBlock()
        fetchLabels()
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

    private fun addTextBlock(){

        val newTextBlock = BubbleModel.BubbleContentBlock(
            type = ContentType.TEXT,
            content = "",
            position = _uiState.value.bubble.contentBlocks.size,
        )

        val updatedContentBlocks = _uiState.value.bubble.contentBlocks +  newTextBlock

        _uiState.value = _uiState.value.copy(
            bubble = _uiState.value.bubble.copy(
                contentBlocks = updatedContentBlocks
            )
        )


    }


    fun addContentBlock(imagePath: String) {

        val newImageBlock = BubbleModel.BubbleContentBlock(
            type = ContentType.IMAGE,
            content = imagePath,
            position = _uiState.value.bubble.contentBlocks.size,
        )

        val newTextBlock = BubbleModel.BubbleContentBlock(
            type = ContentType.TEXT,
            content = "",
            position = _uiState.value.bubble.contentBlocks.size+1,
        )

        val updatedContentBlocks = _uiState.value.bubble.contentBlocks + newImageBlock+ newTextBlock

        _uiState.value = _uiState.value.copy(
            bubble = _uiState.value.bubble.copy(
                contentBlocks = updatedContentBlocks
            )
        )

    }

    fun updateLabel(newLabels: List<LabelModel>) {
        _uiState.value = _uiState.value.copy(
            bubble = _uiState.value.bubble.copy(
                labels = newLabels
            )
        )
    }


    fun deleteContentBlock() {

    }

    fun saveBubble() {

    }

    fun updateBubble(updatedBubble: BubbleModel) {
        _uiState.update { currentState ->
            currentState.copy(bubble = updatedBubble)
        }
    }





}