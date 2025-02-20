package com.umc.edison.presentation.artletter

import com.umc.edison.domain.usecase.artletter.GetAllArtLettersUseCase
import com.umc.edison.domain.usecase.artletter.GetSortedArtLettersUseCase
import com.umc.edison.domain.usecase.artletter.ScrapArtLetterUseCase
import com.umc.edison.domain.usecase.artletter.GetEditorPickUseCase
import com.umc.edison.domain.usecase.mypage.GetLogInStateUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ArtLetterHomeViewModel @Inject constructor(
    private val getLogInStateUseCase: GetLogInStateUseCase,
    private val getEditorPickUseCase: GetEditorPickUseCase,
    private val getAllArtLettersUseCase: GetAllArtLettersUseCase,
    private val getSortedArtLettersUseCase: GetSortedArtLettersUseCase,
    private val scrapArtLetterUseCase: ScrapArtLetterUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ArtLetterHomeState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        getLoginState()
    }

    private fun getLoginState() {
        collectDataResource(
            flow = getLogInStateUseCase(),
            onSuccess = { isLoggedIn ->
                _uiState.update { it.copy(isLoggedIn = isLoggedIn) }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            }
        )
    }

    fun fetchAllArtLetters() {
        collectDataResource(
            flow = getAllArtLettersUseCase(),
            onSuccess = { artLetters ->
                _uiState.update { it.copy(artLetters = artLetters.toPresentation()) }
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

    fun fetchSortedArtLetters(sortBy: String) {
        collectDataResource(
            flow = getSortedArtLettersUseCase(sortBy),
            onSuccess = { artLetters ->
                _uiState.update { it.copy(artLetters = artLetters.toPresentation()) }
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

    fun fetchEditorsPick() {
        collectDataResource(
            flow = getEditorPickUseCase(),
            onSuccess = { artLetters ->
                _uiState.update { it.copy(editorsPick = artLetters.toPresentation()) }
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
