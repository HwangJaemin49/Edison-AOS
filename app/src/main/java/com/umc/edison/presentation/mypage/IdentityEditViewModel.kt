package com.umc.edison.presentation.mypage

import androidx.lifecycle.SavedStateHandle
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.KeywordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class IdentityEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(IdentityEditState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        val id = savedStateHandle.get<Int>("id") ?: throw IllegalArgumentException("id is required")
        fetchIdentityTestResult(id)
    }

    private fun fetchIdentityTestResult(id: Int) {

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