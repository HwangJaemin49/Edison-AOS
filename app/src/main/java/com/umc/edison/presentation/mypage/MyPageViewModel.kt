package com.umc.edison.presentation.mypage

import com.umc.edison.domain.usecase.user.GetLogInStateUseCase
import com.umc.edison.domain.usecase.identity.GetAllMyIdentityResultsUseCase
import com.umc.edison.domain.usecase.artletter.GetAllScrappedArtLettersUseCase
import com.umc.edison.domain.usecase.user.GetMyProfileInfoUseCase
import com.umc.edison.presentation.ToastManager
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toCategoryPresentation
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    toastManager: ToastManager,
    private val getLogInStateUseCase: GetLogInStateUseCase,
    private val getMyProfileInfoUseCase: GetMyProfileInfoUseCase,
    private val getAllMyIdentityResultsUseCase: GetAllMyIdentityResultsUseCase,
    private val getAllScrappedArtLettersUseCase: GetAllScrappedArtLettersUseCase,
) : BaseViewModel(toastManager) {
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
        )
    }

    private fun initMyPage() {
        fetchProfileInfo()
        fetchMyIdentityKeyword()
        fetchScrapBoard()
    }

    private fun fetchProfileInfo() {
        collectDataResource(
            flow = getMyProfileInfoUseCase(),
            onSuccess = { user ->
                _uiState.update { it.copy(user = user.toPresentation()) }
            },
        )
    }

    private fun fetchMyIdentityKeyword() {
        collectDataResource(
            flow = getAllMyIdentityResultsUseCase(),
            onSuccess = { identities ->
                _uiState.update { state ->
                    state.copy(
                        identities = identities.take(3).map { it.toPresentation() },
                        interest = identities[3].toPresentation(),
                    )
                }
            },
        )
    }

    private fun fetchScrapBoard() {
        collectDataResource(
            flow = getAllScrappedArtLettersUseCase(),
            onSuccess = { scrapArtLetters ->
                _uiState.update {
                    it.copy(
                        myArtLetterCategories = scrapArtLetters.map { artLetterCategory ->
                            artLetterCategory.toCategoryPresentation()
                        }
                    )
                }
            },
        )
    }
}
