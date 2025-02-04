package com.umc.edison.presentation.edison

import android.content.Context
import android.net.Uri
import android.text.Html
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.model.ContentType
import com.umc.edison.domain.usecase.bubble.AddBubbleUseCase
import com.umc.edison.domain.usecase.bubble.GetAllBubblesUseCase
import com.umc.edison.domain.usecase.bubble.GetBubbleUseCase
import com.umc.edison.domain.usecase.bubble.UpdateBubbleUseCase
import com.umc.edison.domain.usecase.label.AddLabelUseCase
import com.umc.edison.domain.usecase.label.GetAllLabelsUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.label.LabelEditMode
import com.umc.edison.presentation.model.BubbleModel
import com.umc.edison.presentation.model.ContentBlockModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.presentation.model.toPresentation
import com.umc.edison.ui.components.IconType
import com.umc.edison.ui.components.ListStyle
import com.umc.edison.ui.components.TextStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class BubbleInputViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAllLabelsUseCase: GetAllLabelsUseCase,
    private val getBubbleUseCase: GetBubbleUseCase,
    private val getAllBubblesUseCase: GetAllBubblesUseCase,
    private val addLabelUseCase: AddLabelUseCase,
    private val addBubbleUseCase: AddBubbleUseCase,
    private val updateBubbleUseCase: UpdateBubbleUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(BubbleInputState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        val id: Int = savedStateHandle["id"] ?: throw IllegalArgumentException("ID is required")
        fetchBubble(id)
        addTextBlock()
        fetchLabels()
        fetchBubbles()
    }

    private fun fetchBubble(bubbleId: Int) {
        if (bubbleId == 0) return

        collectDataResource(
            flow = getBubbleUseCase(bubbleId),
            onSuccess = { bubble ->
                _uiState.update {
                    it.copy(
                        bubble = bubble.toPresentation(),
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

    private fun fetchBubbles() {
        collectDataResource(
            flow = getAllBubblesUseCase(),
            onSuccess = { bubbles ->
                _uiState.update { it.copy(bubbles = bubbles.toPresentation()) }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            },
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

    fun saveLabel(label: LabelModel) {
        collectDataResource(
            flow = addLabelUseCase(label.toDomain()),
            onSuccess = {
                updateLabelEditMode(LabelEditMode.EDIT)
                fetchLabels()
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

    fun toggleLabelSelection(label: LabelModel) {
        if (_uiState.value.bubble.labels.contains(label)) {
            _uiState.update {
                it.copy(
                    bubble = it.bubble.copy(labels = it.bubble.labels - label)
                )
            }
        } else if (_uiState.value.bubble.labels.size >= 3) {
            _uiState.update { it.copy(toastMessage = "최대 3개까지 선택할 수 있습니다.") }
        } else {
            _uiState.update {
                it.copy(
                    bubble = it.bubble.copy(labels = it.bubble.labels + label)
                )
            }
        }
    }

    fun updateIcon(iconType: IconType) {
        if (_uiState.value.selectedIcon == IconType.CAMERA || _uiState.value.selectedIcon == IconType.LINK) {
            _uiState.update { it.copy(selectedIcon = IconType.NONE) }
            return
        }

        if (iconType == IconType.NONE) {
            _uiState.update {
                it.copy(
                    selectedTextStyles = emptyList(),
                    selectedListStyle = ListStyle.NONE
                )
            }
        }

        _uiState.update { it.copy(selectedIcon = iconType) }
    }

    fun updateTextStyle(textStyle: TextStyle) {
        val selectedTextStyles = _uiState.value.selectedTextStyles.toMutableList()

        if (selectedTextStyles.contains(textStyle)) {
            selectedTextStyles.remove(textStyle)
        } else {
            selectedTextStyles.add(textStyle)
        }

        _uiState.update { it.copy(selectedTextStyles = selectedTextStyles) }
    }

    fun updateListStyle(listStyle: ListStyle) {
        _uiState.update { it.copy(selectedListStyle = listStyle) }
    }

    fun updateLabel(label: List<LabelModel>) {
        _uiState.update {
            it.copy(
                bubble = it.bubble.copy(labels = label)
            )
        }
    }

    fun updateLabelEditMode(labelEditMode: LabelEditMode) {
        _uiState.update { it.copy(labelEditMode = labelEditMode) }
    }

    private fun addTextBlock() {
        val newTextBlock = ContentBlockModel(
            type = ContentType.TEXT,
            content = "",
            position = _uiState.value.bubble.contentBlocks.size,
        )

        _uiState.update {
            it.copy(
                bubble = it.bubble.copy(
                    contentBlocks = it.bubble.contentBlocks + newTextBlock
                )
            )
        }
    }

    fun addContentBlocks(imagePaths: List<Uri>) {
        val newImageBlocks = mutableListOf<ContentBlockModel>()

        imagePaths.forEach { imagePath ->
            val newImageBlock = ContentBlockModel(
                type = ContentType.IMAGE,
                content = imagePath.toString(),
                position = _uiState.value.bubble.contentBlocks.size,
            )

            newImageBlocks.add(newImageBlock)
        }

        _uiState.update {
            it.copy(
                bubble = it.bubble.copy(
                    contentBlocks = it.bubble.contentBlocks + newImageBlocks
                ),
                isGalleryOpen = false,
                selectedIcon = IconType.NONE
            )
        }

        addTextBlock()
    }

    fun closeGallery() {
        _uiState.update { it.copy(isGalleryOpen = false, selectedIcon = IconType.NONE) }
    }

    fun updateBubbleContent(bubble: BubbleModel) {
        _uiState.update {
            it.copy(
                bubble = bubble
            )
        }

        Log.i("BubbleInputViewModel", "updateBubbleContent: ${bubble.contentBlocks}")
    }

    fun deleteContentBlock(contentBlock: ContentBlockModel) {
        Log.i("BubbleInputViewModel", "before delete: ${_uiState.value.bubble}")

        val currentBubble = _uiState.value.bubble
        val contentBlocks = currentBubble.contentBlocks.toMutableList()

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

        Log.i("BubbleInputViewModel", "after delete: ${_uiState.value.bubble}")
    }

    fun updateBubbleWithLink() {
        saveBubble(true)
    }

    fun saveBubble(isLinked: Boolean = false) {
        if (checkCanSave().not() && isLinked.not()) {
            _uiState.update { it.copy(toastMessage = "내용을 입력해주세요.") }
            return
        }

        convertBrToBackSlash()

        collectDataResource(
            flow = if (_uiState.value.bubble.id == 0) {
                addBubbleUseCase(_uiState.value.bubble.toDomain())
            } else {
                updateBubbleUseCase(_uiState.value.bubble.toDomain())
            },
            onSuccess = { savedBubble ->
                _uiState.update { it.copy(toastMessage = "저장되었습니다.") }

                if (isLinked) {
                    _uiState.update {
                        BubbleInputState.DEFAULT.copy(
                            bubble = BubbleModel(
                                linkedBubble = savedBubble.toPresentation()
                            ),
                            bubbles = it.bubbles + savedBubble.toPresentation()
                        )
                    }
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

    fun checkCanSave(): Boolean {
        val bubble = _uiState.value.bubble
        Log.i("BubbleInputViewModel", "checkCanSave: $bubble")

        if (bubble.title.isNullOrEmpty() && bubble.mainImage.isNullOrEmpty()) {
            return if(bubble.contentBlocks.isEmpty()) {
                false
            } else if (bubble.contentBlocks.size == 1) {
                bubble.contentBlocks[0].content.parseHtml().isNotEmpty() && bubble.contentBlocks[0].content != "<br>"
            } else {
                (bubble.contentBlocks[0].content.parseHtml().isNotEmpty() && bubble.contentBlocks[0].content != "<br>")
                        || (bubble.contentBlocks[1].content.parseHtml().isNotEmpty() && bubble.contentBlocks[1].content != "<br>")
            }
        }

        return true
    }

    private fun convertBrToBackSlash() {
        val contentBlocks = _uiState.value.bubble.contentBlocks.toMutableList()
        val updatedContentBlocks = mutableListOf<ContentBlockModel>()

        contentBlocks.forEachIndexed { _, contentBlock ->
            val contents = contentBlock.content.split("<br>")

            if (contentBlock.type == ContentType.TEXT) {
                val updatedContentBlock = contentBlock.copy(
                    content = if (contents.size > 1) {
                        contents.joinToString(separator = "\n", prefix = "\n").removePrefix("\n")
                    } else {
                        contentBlock.content
                    }
                )
                updatedContentBlocks.add(updatedContentBlock)
            } else {
                updatedContentBlocks.add(contentBlock)
            }
        }

        _uiState.update {
            it.copy(
                bubble = it.bubble.copy(contentBlocks = updatedContentBlocks)
            )
        }
    }

    fun openGallery() {
        _uiState.update { it.copy(isGalleryOpen = true) }
    }

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}

fun String.parseHtml(): String {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
}

private fun saveImageToInternalStorage(context: Context, uri: Uri): Uri {
    val inputStream = context.contentResolver.openInputStream(uri)

    val fileName = "image_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)

    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()

    return Uri.fromFile(file)
}
