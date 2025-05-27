package com.umc.edison.presentation.storage

import android.app.Application
import android.content.Intent
import androidx.core.text.HtmlCompat
import com.umc.edison.domain.usecase.bubble.GetAllRecentBubblesUseCase
import com.umc.edison.domain.usecase.bubble.TrashBubblesUseCase
import com.umc.edison.presentation.ToastManager
import com.umc.edison.presentation.baseBubble.BaseBubbleViewModel
import com.umc.edison.presentation.baseBubble.BubbleStorageMode
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BubbleStorageViewModel @Inject constructor(
    toastManager: ToastManager,
    private val context: Application,
    private val getAllRecentBubblesUseCase: GetAllRecentBubblesUseCase,
    override val trashBubblesUseCase: TrashBubblesUseCase,
) : BaseBubbleViewModel<BubbleStorageMode, BubbleStorageState>(toastManager) {

    override val _uiState = MutableStateFlow(BubbleStorageState.DEFAULT)
    override val uiState = _uiState.asStateFlow()

    fun fetchStorageBubbles() {
        _uiState.update { BubbleStorageState.DEFAULT }
        collectDataResource(
            flow = getAllRecentBubblesUseCase(),
            onSuccess = { bubbles ->
                val sortedBubbles = bubbles.sortedBy { it.date }
                _uiState.update { it.copy(bubbles = sortedBubbles.toPresentation()) }
            },
        )
    }

    fun shareImages() {
        _uiState.update {
            it.copy(
                mode = BubbleStorageMode.EDIT,
            )
        }
    }

    fun shareTexts() {
        val context = this.context
        val selectedBubbles = _uiState.value.selectedBubbles

        if (selectedBubbles.isEmpty()) return

        val textsToShare = selectedBubbles.joinToString("---\n\n") { bubble ->
            val title = bubble.title ?: ""
            val content = bubble.contentBlocks
                .filter { it.type.name == "TEXT" }
                .sortedBy { it.position }
                .joinToString {
                    HtmlCompat.fromHtml(it.content, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                }

            "$title\n\n$content"
        }


        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textsToShare)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // context가 Application이기 때문에 필요함
        context.startActivity(shareIntent)
    }

    override fun refreshDataAfterDeletion() {
        fetchStorageBubbles()
    }
}