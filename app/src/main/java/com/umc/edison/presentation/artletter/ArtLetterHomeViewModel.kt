package com.umc.edison.presentation.artletter

import com.umc.edison.domain.usecase.artletter.GetAllArtLettersUseCase
import com.umc.edison.domain.usecase.artletter.GetSortedArtLettersUseCase
import com.umc.edison.domain.usecase.artletter.ScrapArtLetterUseCase
import com.umc.edison.domain.usecase.artletter.GetAllEditorPickArtLettersUseCase
import com.umc.edison.domain.usecase.user.GetLogInStateUseCase
import com.umc.edison.presentation.ToastManager
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPreviewPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ArtLetterHomeViewModel @Inject constructor(
    toastManager: ToastManager,
    private val getLogInStateUseCase: GetLogInStateUseCase,
    private val getAllEditorPickArtLettersUseCase: GetAllEditorPickArtLettersUseCase,
    private val getAllArtLettersUseCase: GetAllArtLettersUseCase,
    private val getSortedArtLettersUseCase: GetSortedArtLettersUseCase,
    private val scrapArtLetterUseCase: ScrapArtLetterUseCase,
) : BaseViewModel(toastManager) {
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
        )
    }

    fun fetchAllArtLetters() {
        collectDataResource(
            flow = getAllArtLettersUseCase(),
            onSuccess = { artLetters ->
                _uiState.update { it.copy(artLetters = artLetters.toPreviewPresentation()) }
            },
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
        )
    }

    fun fetchSortedArtLetters(sortBy: String) {
        collectDataResource(
            flow = getSortedArtLettersUseCase(sortBy),
            onSuccess = { artLetters ->
                _uiState.update { it.copy(artLetters = artLetters.toPreviewPresentation()) }
            },
        )
    }

    fun fetchEditorsPick() {
        collectDataResource(
            flow = getAllEditorPickArtLettersUseCase(),
            onSuccess = { artLetters ->
                _uiState.update { it.copy(editorsPick = artLetters.toPreviewPresentation()) }
            },
        )
    }

    fun updateShowLoginModal(show: Boolean) {
        _uiState.update { it.copy(showLoginModal = show) }
    }
}
