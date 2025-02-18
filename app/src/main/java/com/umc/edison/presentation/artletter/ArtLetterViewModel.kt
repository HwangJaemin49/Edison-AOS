package com.umc.edison.presentation.artletter


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.umc.edison.domain.usecase.artletter.GetAllArtLettersUseCase
import com.umc.edison.domain.usecase.artletter.GetArtLetterDetailUseCase
import com.umc.edison.domain.usecase.artletter.GetSortedArtLettersUseCase
import com.umc.edison.domain.usecase.artletter.PostArtLetterLikeUseCase
import com.umc.edison.domain.usecase.artletter.PostArtLetterScrapUseCase
import com.umc.edison.domain.usecase.artletter.PostEditorPickUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ArtLetterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAllArtLettersUseCase: GetAllArtLettersUseCase,
    private val getArtLetterDetailUseCase: GetArtLetterDetailUseCase,
    private val getSortedArtLettersUseCase: GetSortedArtLettersUseCase,
    private val postArtLetterLikeUseCase: PostArtLetterLikeUseCase,
    private val postArtLetterScrapUseCase: PostArtLetterScrapUseCase,
    private val postEditorPickUseCase: PostEditorPickUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ArtLetterState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    private val _artLetterDetail = MutableStateFlow(ArtLetterDetailState.DEFAULT)
    val artLetterDetail = _artLetterDetail.asStateFlow()

    private val _likeState = MutableStateFlow(ArtLetterMarkState.DEFAULT)
    val likeState = _likeState.asStateFlow()

    private val _scrapState = MutableStateFlow(ArtLetterScrapState.DEFAULT)
    val scrapState = _scrapState.asStateFlow()


    private val _uiEditorPickState = MutableStateFlow(EditorPickArtLetterState.DEFAULT)
    val uiEditorPickState = _uiEditorPickState.asStateFlow()

    private val _scrapStatus = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val scrapStatus: StateFlow<Map<Int, Boolean>> = _scrapStatus.asStateFlow()

    init {
        val artletterId: Int? = savedStateHandle["artletterId"]
        if (artletterId != null) {
            fetchArtLetterDetail(artletterId)
        } else {
            fetchAllArtLetters()
        }
    }

    private fun fetchAllArtLetters() {
        Log.d("ArtLetterViewModel", "fetchAllArtLetters() 호출됨")

        collectDataResource(
            flow = getAllArtLettersUseCase(),
            onSuccess = { artletters ->
                _uiState.update { it.copy(artletters = artletters.toPresentation()) }
            },
            onError = { error ->
                Log.e("ArtLetterViewModel", "오류 발생: ${error.message}", error)
                _uiState.update { it.copy(error = error) }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                Log.d("ArtLetterViewModel", "fetchAllArtLetters() 완료됨")
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    fun fetchArtLetterDetail(latterId: Int) {
        Log.d("ArtLetterViewModel", "fetchArtLetterDetail() 호출됨")

        collectDataResource(
            flow = getArtLetterDetailUseCase(latterId),
            onSuccess = {  artletter ->
                _artLetterDetail.update {
                    it.copy(artletter = artletter.toPresentation())}
                Log.d("ArtLetterViewModel", "fetchArtLetterDetail(): $artletter")
            },
            onError = { error ->
                _uiState.update { it.copy(error = error) }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                Log.d("ArtLetterViewModel", "fetchArtLetterDetail() 완료됨")
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    fun postArtLetterLike(artletterId: Int) {
        collectDataResource(
            flow = postArtLetterLikeUseCase(artletterId),
            onSuccess = { result ->
                _likeState.update { it.copy(result = result.toPresentation()) }
                fetchArtLetterDetail(artletterId)
                fetchAllArtLetters()
                Log.d("LikeTest", "isLiked: ${result.liked}")
            },
            onError = { error ->
                _likeState.update { it.copy(error = error) }
            },
            onLoading = {
                _likeState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _likeState.update { it.copy(isLoading = false) }
            }
        )
    }

    fun postArtLetterScrap(artletterId: Int) {
        collectDataResource(
            flow = postArtLetterScrapUseCase(artletterId),
            onSuccess = { result ->
                _scrapState.update { it.copy(result = result.toPresentation()) }
                fetchArtLetterDetail(artletterId)
                fetchAllArtLetters()
                Log.d("LikeTest", "isLiked: ${result.scrapped}")
            },
            onError = { error ->
                _scrapState.update { it.copy(error = error) }
            },
            onLoading = {
                _scrapState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _scrapState.update { it.copy(isLoading = false) }
            }
        )
    }

    fun fetchSortedArtLetters(sortBy: String) {
        collectDataResource(
            flow = getSortedArtLettersUseCase(sortBy),
            onSuccess = { artletters ->
                _uiState.update { it.copy(artletters = artletters.toPresentation()) }
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

    fun postEditorPickArtLetter(artletterIds: List<Int>) {
        collectDataResource(
            flow = postEditorPickUseCase(artletterIds),
            onSuccess = { artletters ->
                _uiEditorPickState.update { it.copy(artletters = artletters.toPresentation()) }
            },
            onError = { error ->
                _uiEditorPickState.update { it.copy(error = error) }
            },
            onLoading = {
                _uiEditorPickState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _uiEditorPickState.update { it.copy(isLoading = false) }
            }
        )
    }

    override fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}
