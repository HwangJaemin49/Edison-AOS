package com.umc.edison.presentation.artletter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.edison.domain.usecase.artletter.GetArtLetterDetailUseCase
import com.umc.edison.domain.usecase.artletter.GetRandomArtLettersUseCase
import com.umc.edison.domain.usecase.artletter.LikeArtLetterUseCase
import com.umc.edison.domain.usecase.artletter.ScrapArtLetterUseCase
import com.umc.edison.domain.usecase.mypage.GetLogInStateUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtLetterDetailViewModel @Inject constructor(
    private val getArtLetterDetailUseCase: GetArtLetterDetailUseCase,
    private val likeArtLetterUseCase: LikeArtLetterUseCase,
    private val scrapArtLetterUseCase: ScrapArtLetterUseCase,
    private val getRandomArtLettersUseCase: GetRandomArtLettersUseCase,
    private val getLogInStateUseCase: GetLogInStateUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ArtLetterDetailState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun loadArtLetter(id: String) {
        val intId = id.toIntOrNull()
        if (intId == null) {
            _uiState.update { it.copy(error = Exception("Invalid artLetterId: $id")) }
            return
        }

        fetchArtLetterDetail(intId)
        fetchRandomArtLetters()
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

    private fun fetchArtLetterDetail(id: Int) {
        collectDataResource(
            flow = getArtLetterDetailUseCase(id),
            onSuccess = { artLetterDetail ->
                _uiState.update { it.copy(artLetter = artLetterDetail.toPresentation()) }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            },
            onLoading = { _uiState.update { it.copy(isLoading = true) } },
            onComplete = { _uiState.update { it.copy(isLoading = false) } }
        )
    }

    private fun fetchRandomArtLetters() {
        collectDataResource(
            flow = getRandomArtLettersUseCase(),
            onSuccess = { artLetters ->
                val unique = artLetters.filter { it.artLetterId != _uiState.value.artLetter.artLetterId }
                _uiState.update { it.copy(relatedArtLetters = unique.toPresentation()) }
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            }
        )
    }

    fun likeArtLetter() {
        if (!_uiState.value.isLoggedIn) {
            _uiState.update { it.copy(showLoginModal = true) }
            return
        }

        val artLetter = _uiState.value.artLetter

        collectDataResource(
            flow = likeArtLetterUseCase(artLetter.artLetterId),
            onSuccess = {
                _uiState.update {
                    it.copy(
                        artLetter = artLetter.copy(
                            liked = !artLetter.liked,
                            likesCnt = if (artLetter.liked) artLetter.likesCnt - 1 else artLetter.likesCnt + 1
                        )
                    )
                }
            },
            onError = { error -> _uiState.update { it.copy(error = error) } }
        )
    }

    fun scrapArtLetter() {
        if (!_uiState.value.isLoggedIn) {
            _uiState.update { it.copy(showLoginModal = true) }
            return
        }

        val artLetter = _uiState.value.artLetter

        collectDataResource(
            flow = scrapArtLetterUseCase(artLetter.artLetterId),
            onSuccess = {
                _uiState.update {
                    it.copy(
                        artLetter = artLetter.copy(scraped = !artLetter.scraped)
                    )
                }
            },
            onError = { error -> _uiState.update { it.copy(error = error) } }
        )
    }

    fun scrapArtLetter(id: Int) {
        if (!_uiState.value.isLoggedIn) {
            _uiState.update { it.copy(showLoginModal = true) }
            return
        }

        if (id == _uiState.value.artLetter.artLetterId) {
            scrapArtLetter()
        } else {
            val relatedArtLetter = _uiState.value.relatedArtLetters.find { it.artLetterId == id }

            if (relatedArtLetter != null) {
                collectDataResource(
                    flow = scrapArtLetterUseCase(id),
                    onSuccess = {
                        _uiState.update {
                            it.copy(
                                relatedArtLetters = it.relatedArtLetters.map { artLetter ->
                                    if (artLetter.artLetterId == id) {
                                        artLetter.copy(scraped = !artLetter.scraped)
                                    } else {
                                        artLetter
                                    }
                                }
                            )
                        }
                    },
                    onError = { error -> _uiState.update { it.copy(error = error) } }
                )
            }
        }
    }

    fun updateShowLoginModal(show: Boolean) {
        _uiState.update { it.copy(showLoginModal = show) }
    }

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}
