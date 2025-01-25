package com.umc.edison.presentation.my_edison

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.umc.edison.domain.model.ContentType
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.BubbleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    fun deleteContentBlock() {

    }

    fun saveBubble() {

    }

    fun updateBubble(updatedBubble: BubbleModel) {
        _uiState.update { currentState ->
            currentState.copy(bubble = updatedBubble)
        }
    }


    fun makeList(){

    }




}