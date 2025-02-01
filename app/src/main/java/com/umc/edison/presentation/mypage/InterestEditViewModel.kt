package com.umc.edison.presentation.mypage

import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.model.InterestCategory
import com.umc.edison.domain.usecase.mypage.GetMyInterestResultUseCase
import com.umc.edison.domain.usecase.mypage.UpdateInterestUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.KeywordModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InterestEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMyInterestResultUseCase: GetMyInterestResultUseCase,
    private val updateInterestUseCase: UpdateInterestUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(InterestEditState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        val id = savedStateHandle.get<Int>("id") ?: throw IllegalArgumentException("id is required")
        fetchInterestTestResult(id)
    }

    private fun fetchInterestTestResult(id: Int) {
        val category = InterestCategory.entries[id]

        collectDataResource(
            flow = getMyInterestResultUseCase(category),
            onSuccess = { interest ->
                _uiState.update {
                    it.copy(interest = interest.toPresentation())
                }
            },
            onError = { error ->
                _uiState.update {
                    it.copy(
                        error = error,
                        errorMessage = error.message
                    )
                }
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
        if (uiState.value.interest.selectedKeywords.contains(keyword)) {
            _uiState.update {
                it.copy(
                    interest = it.interest.copy(
                        selectedKeywords = it.interest.selectedKeywords - keyword
                    )
                )
            }
        } else {
            if (uiState.value.interest.selectedKeywords.size >= 5) {
                _uiState.update {
                    val error = Throwable("최대 5개의 키워드를 선택할 수 있습니다.")
                    it.copy(
                        error = error,
                        errorMessage = error.message
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        interest = it.interest.copy(
                            selectedKeywords = it.interest.selectedKeywords + keyword
                        )
                    )
                }
            }
        }
    }

    fun updateIdentity() {
        collectDataResource(
            flow = updateInterestUseCase(_uiState.value.interest.toDomain()),
            onSuccess = {
                _uiState.update { it.copy(interest = it.interest.copy(options = it.interest.selectedKeywords)) }
            },
            onError = { error ->
                _uiState.update {
                    it.copy(
                        error = error,
                        errorMessage = error.message
                    )
                }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    override fun clearError() {
        _uiState.update { it.copy(error = null, errorMessage = null) }
    }
}