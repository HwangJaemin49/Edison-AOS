package com.umc.edison.presentation.mypage

import com.umc.edison.domain.model.InterestCategory
import com.umc.edison.domain.usecase.mypage.GetLogInStateUseCase
import com.umc.edison.domain.usecase.mypage.GetAllMyIdentityResultsUseCase
import com.umc.edison.domain.usecase.mypage.GetMyInterestResultUseCase
import com.umc.edison.domain.usecase.mypage.GetMyScrapArtLettersUseCase
import com.umc.edison.domain.usecase.mypage.GetProfileInfoUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getLogInStateUseCase: GetLogInStateUseCase,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val getAllMyIdentityResultsUseCase: GetAllMyIdentityResultsUseCase,
    private val getMyInterestResultUseCase: GetMyInterestResultUseCase,
    private val getMyScrapArtLettersUseCase: GetMyScrapArtLettersUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(MyPageState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun fetchLoginState() {
        collectDataResource(
            flow = getLogInStateUseCase(),
            onSuccess = { isLoggedIn ->
                _uiState.update { it.copy(isLoggedIn = isLoggedIn) }

                if (isLoggedIn) {
                    initMyPage()
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

    private fun initMyPage() {
        fetchProfileInfo()
        fetchMyIdentityKeyword()
        fetchInterestKeyword()
        fetchScrapBoard()
    }

    private fun fetchProfileInfo() {
        collectDataResource(
            flow = getProfileInfoUseCase(),
            onSuccess = { user ->
                _uiState.update { it.copy(user = user.toPresentation()) }
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

    private fun fetchMyIdentityKeyword() {
        collectDataResource(
            flow = getAllMyIdentityResultsUseCase(),
            onSuccess = { identityKeywords ->
                _uiState.update { state ->
                    state.copy(identities = identityKeywords.map { it.toPresentation() })
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

    private fun fetchInterestKeyword() {
        collectDataResource(
            flow = getMyInterestResultUseCase(InterestCategory.INSPIRATION),
            onSuccess = { interestKeyword ->
                _uiState.update { it.copy(interest = interestKeyword.toPresentation()) }
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

    private fun fetchScrapBoard() {
        collectDataResource(
            flow = getMyScrapArtLettersUseCase(),
            onSuccess = { scrapArtLetters ->
                _uiState.update {
                    it.copy(
                        myArtLetterCategories = scrapArtLetters.map { artLetterCategory ->
                            artLetterCategory.toPresentation()
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