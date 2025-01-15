package com.umc.edison.presentation.space

import android.util.Log
import com.umc.edison.domain.usecase.label.AddLabelUseCase
import com.umc.edison.domain.usecase.label.GetAllLabelsUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LabelListViewModel @Inject constructor(
    private val getAllLabelsUseCase: GetAllLabelsUseCase,
    private val addLabelUseCase: AddLabelUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(LabelListState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        fetchLabels()
    }

    private fun fetchLabels() {
        collectDataResource(
            flow = getAllLabelsUseCase(),
            onSuccess = { labels ->
                Log.d("LabelListViewModel", "fetchLabels: $labels")
                _uiState.update { it.copy(labels = labels.toPresentation()) }
            },
            onError = { error ->
                Log.e("LabelListViewModel", "fetchLabels: $error")
                _uiState.update { it.copy(error = error) }
            },
            onLoading = {
                Log.d("LabelListViewModel", "fetchLabels: loading")
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                Log.d("LabelListViewModel", "fetchLabels: complete")
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }
}