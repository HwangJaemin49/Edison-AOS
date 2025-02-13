package com.umc.edison.presentation.edison

import com.umc.edison.domain.usecase.bubble.GetAllBubblesUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class  MyEdisonViewModel @Inject constructor (
    private val getAllBubblesUseCase: GetAllBubblesUseCase,
): BaseViewModel(

) {

    private val _uiState = MutableStateFlow( MyEdisonState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        fetchBubbles()
    }

    fun isBubbleExist(): Boolean {
        return _uiState.value.bubbles.isNotEmpty()
    }

    private fun fetchBubbles() {
        collectDataResource(
            flow = getAllBubblesUseCase(),
            onSuccess = { bubbles ->
                _uiState.update { it.copy(bubbles = bubbles.toPresentation().filter {
                        bubble -> bubble.id != _uiState.value.bubble.id
                }) }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            },
        )
    }



    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}
