package com.umc.edison.presentation.mypage

import com.umc.edison.domain.usecase.mypage.GetMyScrapArtLettersUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ScrapBoardViewModel @Inject constructor(
    private val getMyScrapArtLettersUseCase: GetMyScrapArtLettersUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(ScrapBoardState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        fetchScrapBoard()
    }

    private fun fetchScrapBoard() {
        collectDataResource(
            flow = getMyScrapArtLettersUseCase(),
            onSuccess = { scrapArtLetters ->
                _uiState.update {
                    it.copy(
                        myArtLetterCategories = scrapArtLetters.map { artLetterCategory ->
                            artLetterCategory.toPresentation()
                        }
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

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }

}