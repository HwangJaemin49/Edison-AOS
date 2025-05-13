package com.umc.edison.presentation.storage

import android.app.Application
import android.content.Intent
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewModelScope
import com.umc.edison.domain.usecase.bubble.SoftDeleteBubblesUseCase
import com.umc.edison.domain.usecase.bubble.GetStorageBubbleUseCase
import com.umc.edison.domain.usecase.sync.SyncDataUseCase
import com.umc.edison.presentation.baseBubble.BaseBubbleViewModel
import com.umc.edison.presentation.baseBubble.BubbleStorageMode
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BubbleStorageViewModel @Inject constructor(
    private val context: Application,
    private val getStorageBubbleUseCase: GetStorageBubbleUseCase,
    override val softDeleteBubblesUseCase: SoftDeleteBubblesUseCase,
    override val syncDataUseCase: SyncDataUseCase,
) : BaseBubbleViewModel<BubbleStorageMode, BubbleStorageState>() {

    override val _uiState = MutableStateFlow(BubbleStorageState.DEFAULT)
    override val uiState = _uiState.asStateFlow()



    fun fetchStorageBubbles() {
        _uiState.update { BubbleStorageState.DEFAULT }
        collectDataResource(
            flow = getStorageBubbleUseCase(),
            onSuccess = { bubbles ->
                val sortedBubbles = bubbles.sortedBy { it.date }
                _uiState.update { it.copy(bubbles = sortedBubbles.toPresentation()) }
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

    fun shareImages() {
        _uiState.update {
            it.copy(
                mode = BubbleStorageMode.EDIT,
                toastMessage = "서비스 준비 중입니다."
            )
        }
    }

    fun shareTexts() {
        val context = this.context
        val selectedBubbles = _uiState.value.selectedBubbles

        if (selectedBubbles.isEmpty()) return

        val textsToShare = selectedBubbles.map { bubble ->
            val title = bubble.title ?: ""
            val content = bubble.contentBlocks
                .filter { it.type.name == "TEXT" }
                .sortedBy { it.position }
                .joinToString {
                    HtmlCompat.fromHtml(it.content, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                }

            "$title\n\n$content"
        }.joinToString("---\n\n")


        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textsToShare)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // context가 Application이기 때문에 필요함
        context.startActivity(shareIntent)
    }

    fun syncData() {
        viewModelScope.launch {
            try {
                syncDataUseCase()
            } catch (e: Throwable) {
                _uiState.update { it.copy(error = e) }
            }
        }
    }



    override fun refreshDataAfterDeletion() {
        fetchStorageBubbles()
    }
}