package com.umc.edison.presentation.label

import android.util.Log
import com.umc.edison.domain.usecase.label.AddLabelUseCase
import com.umc.edison.domain.usecase.label.DeleteLabelUseCase
import com.umc.edison.domain.usecase.label.GetAllLabelsUseCase
import com.umc.edison.domain.usecase.label.UpdateLabelUseCase
import com.umc.edison.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LabelListViewModel @Inject constructor(
    private val getAllLabelsUseCase: GetAllLabelsUseCase,
    private val addLabelUseCase: AddLabelUseCase,
    private val updateLabelUseCase: UpdateLabelUseCase,
    private val deleteLabelUseCase: DeleteLabelUseCase,
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
                labels.sortedByDescending { it.bubbles.size }
                _uiState.update { it.copy(labels = labels.toLabelListPresentation()) }
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

    fun updateEditMode(labelEditMode: LabelEditMode) {
        _uiState.update { it.copy(labelEditMode = labelEditMode) }
    }

    fun updateSelectedLabel(label: LabelListModel) {
        _uiState.update { it.copy(selectedLabel = label) }
    }

    fun confirmLabelModal(label: LabelListModel) {
        if (uiState.value.labelEditMode == LabelEditMode.ADD) {
            collectDataResource(
                flow = addLabelUseCase(label.toDomain()),
                onSuccess = {
                    updateEditMode(LabelEditMode.NONE)
                    _uiState.update { it.copy(selectedLabel = LabelListState.DEFAULT.selectedLabel) }
                },
                onError = { error ->
                    Log.e("addLabel", "onError: $error")
                    _uiState.update { it.copy(error = error) }
                },
                onLoading = {
                    Log.d("addLabel", "onLoading")
                    _uiState.update { it.copy(isLoading = true) }
                },
                onComplete = {
                    fetchLabels()
                }
            )
        } else if (uiState.value.labelEditMode == LabelEditMode.EDIT) {
            collectDataResource(
                flow = updateLabelUseCase(label.toDomain()),
                onSuccess = {
                    updateEditMode(LabelEditMode.NONE)
                    _uiState.update { it.copy(selectedLabel = LabelListState.DEFAULT.selectedLabel) }
                },
                onError = { error ->
                    _uiState.update { it.copy(error = error) }
                },
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onComplete = {
                    fetchLabels()
                }
            )
        }
    }

    fun deleteSelectedLabel() {
        collectDataResource(
            flow = deleteLabelUseCase(_uiState.value.selectedLabel.toDomain()),
            onSuccess = {
                updateEditMode(LabelEditMode.NONE)
                _uiState.update { it.copy(
                    labels = it.labels.filter { label -> label.id != it.selectedLabel.id },
                    selectedLabel = LabelListState.DEFAULT.selectedLabel
                ) }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                fetchLabels()
            }
        )
    }
}