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
        fetchLabels()
        fetchBubbles()
    }

    private fun fetchBubble(bubbleId: Int) {
        if (bubbleId == 0) {
            addTextBlockToFront()
            return
        }

        collectDataResource(
            flow = getBubbleUseCase(bubbleId),
            onSuccess = { bubble ->
                _uiState.update {
                    it.copy(
                        bubble = bubble.toPresentation(),
                        selectedLabels = bubble.labels.toPresentation()
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
                addTextBlockToFront()
            }
        )
    }

    private fun fetchBubbles() {
        collectDataResource(
            flow = getAllBubblesUseCase(),
            onSuccess = { bubbles ->
                _uiState.update { it.copy(bubbles = bubbles.toPresentation().filter {
                    bubble -> bubble.id != _uiState.value.bubble.id
                }) }
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
                _uiState.update {
                    it.copy(
                        labels = labels.filter { label -> label.id != 0 }.toMutableList()
                            .toPresentation()
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

    fun updateSelectedLabels(labels: List<LabelModel>) {
        _uiState.update {
            it.copy(
                bubble = it.bubble.copy(
                    labels = labels
                ),
                selectedLabels = labels
            )
        }
    }

    fun updateIcon(iconType: IconType) {
        if (iconType == IconType.CAMERA && _uiState.value.selectedIcon == IconType.CAMERA) {
            _uiState.update { it.copy(selectedIcon = IconType.NONE) }
            return
        }

        if (iconType == IconType.LINK && _uiState.value.selectedIcon == IconType.LINK) {
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

        if (iconType == IconType.TAG) {
            fetchLabels()
        }

        if (iconType == IconType.TAG) {
            updateLabelEditMode(LabelEditMode.EDIT)
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

    fun updateLabelEditMode(labelEditMode: LabelEditMode) {
        _uiState.update { it.copy(labelEditMode = labelEditMode) }
    }

    private fun addTextBlock() {
        val newTextBlock = ContentBlockModel(
            type = ContentType.TEXT,
            content = "",
        )

        if (_uiState.value.bubble.contentBlocks.isEmpty()) {
            _uiState.update {
                it.copy(
                    bubble = it.bubble.copy(
                        contentBlocks = listOf(newTextBlock)
                    )
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    bubble = it.bubble.copy(
                        contentBlocks = it.bubble.contentBlocks + newTextBlock
                    )
                )
            }
        }
    }

    private fun addTextBlockToFront() {
        if (_uiState.value.bubble.contentBlocks.isEmpty()) {
            addTextBlock()
            return
        }

        if (_uiState.value.bubble.contentBlocks[0].type == ContentType.TEXT && _uiState.value.bubble.contentBlocks.last().type == ContentType.TEXT) {
            return
        }

        if (_uiState.value.bubble.contentBlocks[0].type == ContentType.TEXT) {
            return
        }

        val newTextBlock = ContentBlockModel(
            type = ContentType.TEXT,
            content = "",
        )

        _uiState.update {
            val updatedContentBlocks = listOf(newTextBlock) + it.bubble.contentBlocks
            it.copy(
                bubble = it.bubble.copy(
                    contentBlocks = updatedContentBlocks
                )
            )
        }

        if (_uiState.value.bubble.contentBlocks.last().type == ContentType.IMAGE) {
            addTextBlock()
        }
    }

    fun addContentBlocks(imagePaths: List<Uri>) {

        val existingImageCount = _uiState.value.bubble.contentBlocks.count { it.type == ContentType.IMAGE }

        if (existingImageCount+imagePaths.size  > 10) {
            _uiState.update { it.copy(toastMessage = "최대 10장까지 첨부할 수 있습니다.") }
            return
        }

        val newImageBlocks = mutableListOf<ContentBlockModel>()

        imagePaths.forEachIndexed { index,imagePath ->
            val newImageBlock = ContentBlockModel(
                type = ContentType.IMAGE,
                content = imagePath.toString(),
            )


            newImageBlocks.add(newImageBlock)

        }

        _uiState.update {
            it.copy(
                bubble = it.bubble.copy(
                    contentBlocks = it.bubble.contentBlocks + newImageBlocks,
                    mainImage =  it.bubble.mainImage
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

        checkCanSave()
    }

    fun deleteContentBlock(targetIndex: Int) {
        val currentBubble = _uiState.value.bubble
        val contentBlocks = currentBubble.contentBlocks.toMutableList()

        // 유효한 인덱스인지 확인
        if (targetIndex !in contentBlocks.indices) return

        val targetBlock = contentBlocks[targetIndex] // 직접 해당 블록 가져오기

        if (targetBlock.type != ContentType.IMAGE) return

        // 🔹 이미지 블록 로그 출력
        Log.d("delete", "삭제할 이미지 블록 index: $targetIndex, 내용: ${targetBlock.content}")

        // 이미지가 첫 번째 블록일 때
        if (targetIndex == 0) {
            contentBlocks.removeAt(targetIndex)
            _uiState.update { it.copy(bubble = it.bubble.copy(contentBlocks = contentBlocks)) }

            if (contentBlocks.isEmpty() || contentBlocks[0].type == ContentType.IMAGE) {
                addTextBlockToFront()
            }
            return
        }

        // 이미지가 마지막 블록일 때
        if (targetIndex == currentBubble.contentBlocks.lastIndex) {
            contentBlocks.removeAt(targetIndex)
            _uiState.update { it.copy(bubble = it.bubble.copy(contentBlocks = contentBlocks)) }

            // 마지막 블록이 이미지 블록이면 텍스트 블록 추가
            if (contentBlocks.isNotEmpty() && contentBlocks.last().type == ContentType.IMAGE) {
                addTextBlock()
            }
            return
        }

        // 이미지가 중간 블럭일 때 이전 블록과 다음 블록이 TEXT일 경우 연결
        if (contentBlocks[targetIndex - 1].type == ContentType.TEXT && contentBlocks[targetIndex + 1].type == ContentType.TEXT) {

            val previousTextBlock = contentBlocks[targetIndex - 1]
            val nextTextBlock = contentBlocks[targetIndex + 1]

            Log.d("delete","${previousTextBlock}, ${nextTextBlock}")

            val mergedContent = previousTextBlock.content + nextTextBlock.content

            val newContentBlocks = contentBlocks.filterIndexed { index, _ ->
                index != targetIndex && index != targetIndex + 1 // 이미지 블록과 다음 텍스트 블록 제거
            }.toMutableList()

            // 🔹 병합된 텍스트 블록을 이전 텍스트 위치에 추가
            newContentBlocks[targetIndex - 1] = previousTextBlock.copy(content = mergedContent)

            // 🔹 최종 업데이트
            _uiState.update { it.copy(bubble = it.bubble.copy(contentBlocks = newContentBlocks)) }

            Log.d("delete", "✅ 병합 완료: ${newContentBlocks[targetIndex - 1].content}")
            return
        }


        // 기본적인 이미지 블록 삭제
        contentBlocks.removeAt(targetIndex)
        _uiState.update {
            it.copy(
                bubble = it.bubble.copy(contentBlocks = contentBlocks)
            )
        }
    }



    fun updateBubbleWithLink() {
        saveBubble(true)
    }

    fun saveBubble(isLinked: Boolean = false) {
        trimBlankBlock()

        checkCanSave()

        if (!_uiState.value.canSave && isLinked) {
            _uiState.update { it.copy(toastMessage = "내용을 입력해주세요.") }
            addTextBlockToFront()
            return
        }

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

                    addTextBlock()
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

    private fun checkCanSave() {
        var canSave = true

        val bubble = _uiState.value.bubble

        if (bubble.title.isNullOrEmpty() && bubble.mainImage.isNullOrEmpty()) {
            canSave = if (bubble.contentBlocks.isEmpty()) {
                false
            } else if (bubble.contentBlocks.size == 1) {
                bubble.contentBlocks[0].content.parseHtml()
                    .isNotEmpty() && bubble.contentBlocks[0].content != "<br>"
            } else {
                (bubble.contentBlocks[0].content.parseHtml()
                    .isNotEmpty() && bubble.contentBlocks[0].content != "<br>")
                        || (bubble.contentBlocks[1].content.parseHtml()
                    .isNotEmpty() && bubble.contentBlocks[1].content != "<br>")
            }
        }

        _uiState.update { it.copy(canSave = canSave) }
    }

    private fun trimBlankBlock() {
        val contentBlocks = _uiState.value.bubble.contentBlocks.toMutableList()
        val updatedContentBlocks = mutableListOf<ContentBlockModel>()

        contentBlocks.forEachIndexed { _, contentBlock ->
            if (contentBlock.type == ContentType.TEXT) {
                val contents = contentBlock.content.split("<br>")
                val newContent = if (contents.size > 1) {
                    contents.joinToString(separator = "") { it.trim() }
                } else {
                    contentBlock.content
                }

                if (newContent.parseHtml().trim().isNotEmpty()) {
                    val updatedContentBlock = contentBlock.copy(
                        content = newContent,
                    )
                    updatedContentBlocks.add(updatedContentBlock)
                }
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

    fun saveCameraImage(uri: Uri) {
        _uiState.update {
            it.copy(cameraImagePath = uri)
        }
    }

    fun saveCameraImage(context: Context) {
        val imageCnt = _uiState.value.bubble.contentBlocks.count { it.type == ContentType.IMAGE }
        if (imageCnt >= 10) {
            _uiState.update { it.copy(toastMessage = "최대 10장까지 첨부할 수 있습니다.") }
            return
        }

        val savedUri = saveImageToInternalStorage(context, _uiState.value.cameraImagePath!!)
        addContentBlocks(listOf(savedUri))
    }

    fun updateCameraOpen(isOpen: Boolean) {
        _uiState.update { it.copy(isCameraOpen = isOpen) }
    }

    fun addBackLink(bubble: BubbleModel) {
        if (_uiState.value.bubble.backLinks.map { it.id }.contains(bubble.id)) {
            return
        }

        _uiState.update {
            it.copy(
                bubble = it.bubble.copy(
                    backLinks = it.bubble.backLinks + bubble
                )
            )
        }
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

    fun selectMainImage(uri: String?) {
        _uiState.update {
            it.copy(
                bubble = it.bubble.copy(mainImage = uri)
            )
        }
        Log.d("대표설정", "대표 이미지 변경됨: ${_uiState.value.bubble.mainImage}")
    }

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }

    fun updateToastMessage(message: String) {
        _uiState.update { it.copy(toastMessage = message) }
    }
}

fun String.parseHtml(): String {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
}
