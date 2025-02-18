package com.umc.edison.presentation.artletter

import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.usecase.artletter.GetArtLetterDetailUseCase
import com.umc.edison.domain.usecase.artletter.LikeArtLetterUseCase
import com.umc.edison.domain.usecase.artletter.ScrapArtLetterUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ArtLetterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getArtLetterDetailUseCase: GetArtLetterDetailUseCase,
    private val likeArtLetterUseCase: LikeArtLetterUseCase,
    private val scrapArtLetterUseCase: ScrapArtLetterUseCase,
    private val getRandomArtLettersUseCase: GetRandomArtLettersUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(ArtLetterDetailState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        val id = savedStateHandle.get<Int>("id") ?: throw IllegalArgumentException("id is required")
        fetchArtLetterDetail(id)
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

    fun likeArtLetter() {
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

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}