package com.umc.edison.presentation.mypage

import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.model.IdentityCategory
import com.umc.edison.domain.usecase.mypage.GetMyIdentityResultUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.KeywordModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class IdentityEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMyIdentityResultUseCase: GetMyIdentityResultUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(IdentityEditState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        val id = savedStateHandle.get<Int>("id") ?: throw IllegalArgumentException("id is required")
        fetchIdentityTestResult(id)
    }

    private fun fetchIdentityTestResult(id: Int) {
        val category = IdentityCategory.entries[id]

        collectDataResource(
            flow = getMyIdentityResultUseCase(category),
            onSuccess = { identity ->
                _uiState.update {
                    it.copy(
                        identityCategory = identity.category,
                        selectedKeywords = identity.selectedKeywords.map { keyword -> keyword.toPresentation() },
                        options = identity.options.map { keyword -> keyword.toPresentation() }
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

    fun toggleKeyword(keyword: KeywordModel) {
        _uiState.update {
            if (it.selectedKeywords.contains(keyword)) {
                it.copy(
                    selectedKeywords = it.selectedKeywords - keyword
                )
            } else {
                it.copy(
                    selectedKeywords = it.selectedKeywords + keyword
                )
            }
        }
    }
}