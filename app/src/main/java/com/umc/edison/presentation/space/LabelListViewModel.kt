package com.umc.edison.presentation.space

import android.util.Log
import com.umc.edison.domain.usecase.label.AddLabelUseCase
import com.umc.edison.domain.usecase.label.GetAllLabelsUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.LabelModel
import com.umc.edison.presentation.model.toPresentation
import com.umc.edison.ui.space.EditMode
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
                Log.d("fetchLabels", "onSuccess: $labels")
                _uiState.update { it.copy(labels = labels.toPresentation()) }
            },
            onError = { error ->
                Log.d("fetchLabels", "onError: $error")
                _uiState.update { it.copy(error = error) }
            },
            onLoading = {
                Log.d("fetchLabels", "onLoading: ${uiState.value.labels}")
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                Log.d("fetchLabels", "onComplete: ${uiState.value.labels}")
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    fun updateEditMode(editMode: EditMode) {
        _uiState.update { it.copy(editMode = editMode) }
    }

    fun confirmLabelModal(label: LabelModel) {
        if (uiState.value.editMode == EditMode.ADD) {
            collectDataResource(
                flow = addLabelUseCase(label.toDomain()),
                onSuccess = { result ->
                    Log.d("addLabel", "onSuccess: $result")
                },
                onError = { error ->
                    Log.d("addLabel", "onError: $error")
                    _uiState.update { it.copy(error = error) }
                },
                onLoading = {
                    Log.d("addLabel", "onLoading")
                    _uiState.update { it.copy(isLoading = true) }
                },
                onComplete = {
                    fetchLabels()
                    _uiState.update { it.copy(isLoading = false) }
                }
            )
        } else if (uiState.value.editMode == EditMode.EDIT) {
            TODO("Edit label")
        }
    }
}