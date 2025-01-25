package com.umc.edison.presentation.label

import androidx.lifecycle.SavedStateHandle
import com.umc.edison.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LabelDetailViewModel @Inject constructor(
   savedStateHandle: SavedStateHandle,

) : BaseViewModel() {

    private val _uiState = MutableStateFlow(LabelDetailState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        val id: Int = savedStateHandle["id"] ?: throw IllegalArgumentException("ID is required")
        fetchBubbles(id)
    }

    private fun fetchBubbles(id: Int) {
        TODO("Not yet implemented")
    }

}