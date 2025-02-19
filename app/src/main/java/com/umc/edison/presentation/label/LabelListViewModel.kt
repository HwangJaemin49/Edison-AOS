package com.umc.edison.presentation.label

import com.umc.edison.domain.usecase.label.AddLabelUseCase
import com.umc.edison.domain.usecase.label.DeleteLabelUseCase
import com.umc.edison.domain.usecase.label.GetAllLabelsUseCase
import com.umc.edison.domain.usecase.label.UpdateLabelUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.LabelModel
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
    private val updateLabelUseCase: UpdateLabelUseCase,
    private val deleteLabelUseCase: DeleteLabelUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(LabelListState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        fetchLabels()
    }

    fun fetchLabels() {
        _uiState.update { LabelListState.DEFAULT }
        collectDataResource(
            flow = getAllLabelsUseCase(),
            onSuccess = { labels ->
                val sortedLabels = labels.sortedWith(compareBy({ it.id == 0 }, { it.bubbles.size })).reversed()

                _uiState.update { it.copy(labels = sortedLabels.toPresentation()) }
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

    fun updateSelectedLabel(label: LabelModel) {
        _uiState.update { it.copy(selectedLabel = label) }
    }

    fun confirmLabelModal(label: LabelModel) {
        if (uiState.value.labelEditMode == LabelEditMode.ADD) {
            collectDataResource(
                flow = addLabelUseCase(label.toDomain()),
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

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}