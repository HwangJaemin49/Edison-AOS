package com.umc.edison.presentation.artletter

import com.umc.edison.data.model.toDomain
import com.umc.edison.domain.usecase.artletter.GetAllArtLettersUseCase
import com.umc.edison.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ArtLetterViewModel @Inject constructor(
    private val getAllArtLettersUseCase: GetAllArtLettersUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ArtLetterState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchAllArtLetters()
    }

    private fun fetchAllArtLetters() {
        collectDataResource(
            flow = getAllArtLettersUseCase(),
            onSuccess = { artletters ->
                _uiState.update { it.copy(artletters = artletters.toDomain()) }
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
