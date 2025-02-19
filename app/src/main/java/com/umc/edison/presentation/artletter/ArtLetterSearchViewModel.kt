package com.umc.edison.presentation.artletter

import com.umc.edison.domain.usecase.artletter.GetRandomArtLettersUseCase
import com.umc.edison.domain.usecase.artletter.GetSearchArtLettersUseCase
import com.umc.edison.domain.usecase.artletter.ScrapArtLetterUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ArtLetterSearchViewModel @Inject constructor(
    private val getSearchArtLettersUseCase: GetSearchArtLettersUseCase,
    private val getRandomArtLettersUseCase: GetRandomArtLettersUseCase,
    private val scrapArtLetterUseCase: ScrapArtLetterUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(ArtLetterSearchState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    init {
        fetchRecommendedArtLetters()
        fetchRecommendedArtLetters()
    }

    fun searchArtLetters(keyword: String, sortBy: String) {

        if (keyword.isNotBlank()) {
            addSearchHistory(keyword)
        }

        collectDataResource(
            flow = getSearchArtLettersUseCase(keyword, sortBy),
            onSuccess = { artletters ->
                _uiState.update { it.copy(artLetters = artletters.toPresentation()) }
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

    private fun addSearchHistory(keyword: String) {
        _searchHistory.update { currentList ->
            val newList = listOf(keyword) + currentList.filter { it != keyword } // 중복 제거
            newList.take(5) // 최근 검색어 최대 5개 유지
        }
    }

    fun removeSearchHistory(keyword: String) {
        _searchHistory.update { currentList ->
            currentList.filter { it != keyword }
        }
    }

    private fun fetchRecommendedArtLetters() {
        collectDataResource(
            flow = getRandomArtLettersUseCase(),
            onSuccess = { artLetters ->
                _uiState.update { it.copy(recommendedArtLetters = artLetters.toPresentation()) }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            }
        )
    }

    fun postArtLetterScrap(id: Int) {
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