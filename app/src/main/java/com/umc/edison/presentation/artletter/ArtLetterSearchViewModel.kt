package com.umc.edison.presentation.artletter


import com.umc.edison.domain.usecase.artletter.GetArtLetterKeyWordUseCase
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
    private val getArtLetterKeyWordUseCase: GetArtLetterKeyWordUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(ArtLetterSearchState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    private val _keywords = MutableStateFlow(ArtLetterKeyWordState.DEFAULT)
    val keywords = _keywords.asStateFlow()

    init {
        fetchRecommendedArtLetters()
    }

    fun searchArtLetters() {
        if (_uiState.value.query.isEmpty()) {
            return
        }
        collectDataResource(
            flow = getSearchArtLettersUseCase(_uiState.value.query),
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

    fun getSortedArtLetters(sortType: String) {
        collectDataResource(
            flow = getSearchArtLettersUseCase(_uiState.value.query, sortType),
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

    fun updateSearchQuery(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }

//    fun removeSearchHistory(id: Int) {
//        _
//    }

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

    fun updateIsSearchActivated(isSearchActivated: Boolean) {
        _uiState.update {
            it.copy(isSearchActivated = isSearchActivated)
        }
    }

    fun getKeyWords(artletterIds: List<Int>) {
        collectDataResource(
            flow = getArtLetterKeyWordUseCase(artletterIds),
            onSuccess = { keywords -> _keywords.update { it.copy(keywords = keywords.toPresentation()) }
            },
            onError = { error ->
                _keywords.update { it.copy(error = error) }
            },
            onLoading = {
                _keywords.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _keywords.update { it.copy(isLoading = false) }
            }
        )
    }

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}