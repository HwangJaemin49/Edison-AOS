package com.umc.edison.presentation.my_edison

import com.umc.edison.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class BubbleInputViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(BubbleInputState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun addContentBlock() {

    }

    fun deleteContentBlock() {

    }

    fun saveBubble() {

    }

    fun applyTextStyle() {

    }



}