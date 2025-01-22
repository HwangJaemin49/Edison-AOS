package com.umc.edison.presentation.my_edison

import com.umc.edison.domain.model.ContentType
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.BubbleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BubbleInputViewModel @Inject constructor(

) : BaseViewModel() {

    private val _uiState = MutableStateFlow(BubbleInputState.DEFAULT)
    val uiState = _uiState.asStateFlow()



    init {
        addTextBlock()
    }

    private fun addTextBlock(){

        val newTextBlock = BubbleModel.BubbleContentBlock(
            type = ContentType.TEXT,
            content = "내용을 입력하세요",
            position = _uiState.value.bubble.contentBlocks.size,
            contentStyles = mutableListOf()
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
            contentStyles = mutableListOf()
        )

        val newTextBlock = BubbleModel.BubbleContentBlock(
            type = ContentType.TEXT, // 텍스트 블록
            content = "",            // 초기 텍스트
            position = _uiState.value.bubble.contentBlocks.size+1,
            contentStyles = mutableListOf()
        )

        val updatedContentBlocks = _uiState.value.bubble.contentBlocks + newImageBlock+ newTextBlock

        _uiState.value = _uiState.value.copy(
            bubble = _uiState.value.bubble.copy(
                contentBlocks = updatedContentBlocks
            )
        )



    }

    fun deleteContentBlock() {

    }

    fun saveBubble() {

    }




}