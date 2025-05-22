package com.umc.edison.presentation.mypage

import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.usecase.artletter.ScrapArtLetterUseCase
import com.umc.edison.domain.usecase.artletter.GetScrappedArtLettersByCategoryUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPreviewPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ScrapBoardDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getScrappedArtLettersByCategoryUseCase: GetScrappedArtLettersByCategoryUseCase,
    private val scrapArtLetterUseCase: ScrapArtLetterUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(ScrapBoardDetailState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        val category = savedStateHandle.get<String>("name") ?: ""
        fetchScrapArtLetters(category)
    }

    private fun fetchScrapArtLetters(name: String) {
        collectDataResource(
            flow = getScrappedArtLettersByCategoryUseCase(name),
            onSuccess = { artLetters ->
                _uiState.update { it.copy(artLetters = artLetters.toPreviewPresentation()) }
            },
            onLoading = {
                _uiState.update { it.copy(categoryName = name) }
            },
        )
    }

    fun toggleScrap(id: Int) {
        collectDataResource(
            flow = scrapArtLetterUseCase(id),
            onSuccess = {
                _uiState.update {
                    it.copy(
                        artLetters = it.artLetters.map { artLetter ->
                            if (artLetter.artLetterId == id) {
                                artLetter.copy(scraped = !artLetter.scraped)
                            } else {
                                artLetter
                            }
                        }
                    )
                }
            },
        )
    }
}
