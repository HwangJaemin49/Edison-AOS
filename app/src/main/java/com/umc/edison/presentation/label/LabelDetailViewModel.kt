package com.umc.edison.presentation.label

import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.usecase.label.GetLabelDetailUseCase
import com.umc.edison.presentation.base.BaseViewModel
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
        fetchBubbles(id)
    }

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

}