package com.umc.edison.presentation.storage

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
        _uiState.update {
            it.copy(
                mode = BubbleStorageMode.EDIT,
                toastMessage = "서비스 준비 중입니다."
            )
        }
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