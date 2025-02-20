package com.umc.edison.presentation.artletter


import com.umc.edison.domain.usecase.artletter.GetArtLetterCategoryUseCase
import com.umc.edison.domain.usecase.artletter.GetArtLetterKeyWordUseCase
import com.umc.edison.domain.usecase.artletter.GetRandomArtLettersUseCase
import com.umc.edison.domain.usecase.artletter.GetRecentSearchesUseCase
import com.umc.edison.domain.usecase.artletter.GetSearchArtLettersUseCase
import com.umc.edison.domain.usecase.artletter.RemoveRecentSearchUseCase
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
    private val removeRecentSearchUseCase: RemoveRecentSearchUseCase,
    private val getRecentSearchesUseCase: GetRecentSearchesUseCase,
    private val getArtLetterCategoryUseCase: GetArtLetterCategoryUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(ArtLetterSearchState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        fetchRecommendedArtLetters()
        getRecentSearches()
        getKeyWords()
        getCetegories()
    }

    fun searchArtLetters() {
        if (_uiState.value.query.isEmpty()) {
            return
        }

        if (uiState.value.query.trim().isEmpty()) {
            return
        }

        collectDataResource(
            flow = getSearchArtLettersUseCase(_uiState.value.query),
            onSuccess = { artletters ->
                _uiState.update {
                    it.copy(
                        artLetters = artletters.toPresentation(),
                        lastQuery = it.query,
                        isSearchActivated = true
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
                getRecentSearches()
            }
        )
    }

    fun searchArtLetters(query: String) {
        _uiState.update {
            it.copy(query = query)
        }

        searchArtLetters()
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

    private fun getRecentSearches() {
        collectDataResource(
            flow = getRecentSearchesUseCase(),
            onSuccess = { recentSearches ->
                _uiState.update { it.copy(recentSearches = recentSearches) }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            }
        )
    }

    fun updateSearchQuery(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }

    fun removeSearchHistory(keyword: String) {
        collectDataResource(
            flow = removeRecentSearchUseCase(keyword),
            onSuccess = {
                _uiState.update {
                    it.copy(
                        recentSearches = it.recentSearches.filter { search -> search != keyword }
                    )
                }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            }
        )
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
        if (!_uiState.value.isLoggedIn) {
            _uiState.update { it.copy(showLoginModal = true) }
            return
        }

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

    fun getKeyWords() {
        collectDataResource(
            flow = getArtLetterKeyWordUseCase(),
            onSuccess = { keywords ->
                _uiState.update { it.copy(keywords = keywords.toPresentation()) }
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

    fun getCetegories() {
        collectDataResource(
            flow = getArtLetterCategoryUseCase(),
            onSuccess = { categories ->
                _uiState.update {
                    it.copy(
                        categories = categories
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

    fun updateShowLoginModal(show: Boolean) {
        _uiState.update { it.copy(showLoginModal = show) }
    }

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}