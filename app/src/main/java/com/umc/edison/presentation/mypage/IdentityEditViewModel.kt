package com.umc.edison.presentation.mypage

import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.model.IdentityCategory
import com.umc.edison.domain.usecase.mypage.GetMyIdentityResultUseCase
import com.umc.edison.domain.usecase.mypage.UpdateIdentityUseCase
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
    private val getMyIdentityResultUseCase: GetMyIdentityResultUseCase,
    private val updateIdentityUseCase: UpdateIdentityUseCase,
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
                _uiState.update { it.copy(identity = identity.toPresentation()) }
            },
        )
    }

    fun toggleKeyword(keyword: KeywordModel) {
        if (uiState.value.identity.selectedKeywords.contains(keyword)) {
            _uiState.update {
                it.copy(
                    identity = it.identity.copy(
                        selectedKeywords = it.identity.selectedKeywords - keyword
                    )
                )
            }
        } else {
            if (uiState.value.identity.selectedKeywords.size >= 5) {
                _baseState.update {
                    it.copy(toastMessage = "최대 5개의 키워드를 선택할 수 있습니다.")
                }
            } else {
                _uiState.update {
                    it.copy(
                        identity = it.identity.copy(
                            selectedKeywords = it.identity.selectedKeywords + keyword
                        )
                    )
                }
            }
        }
    }

    fun updateIdentity() {
        collectDataResource(
            flow = updateIdentityUseCase(_uiState.value.identity.toDomain()),
            onSuccess = {
                _uiState.update {
                    it.copy(
                        identity = it.identity.copy(options = it.identity.selectedKeywords)
                    )
                }
            },
            onComplete = {
                _uiState.update { it.copy(isEdited = true) }
            }
        )
    }
}
