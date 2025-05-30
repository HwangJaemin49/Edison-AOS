package com.umc.edison.presentation.mypage

import com.umc.edison.domain.usecase.artletter.GetAllScrappedArtLettersUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toCategoryPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ScrapBoardViewModel @Inject constructor(
    private val getAllScrappedArtLettersUseCase: GetAllScrappedArtLettersUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(ScrapBoardState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        fetchScrapBoard()
    }

    private fun fetchScrapBoard() {
        collectDataResource(
            flow = getAllScrappedArtLettersUseCase(),
            onSuccess = { scrapArtLetters ->
                _uiState.update {
                    it.copy(
                        myArtLetterCategories = scrapArtLetters.map { artLetterCategory ->
                            artLetterCategory.toCategoryPresentation()
                        }
                    )
                }
            },
        )
    }
}
