package com.umc.edison.presentation.mypage

import com.umc.edison.domain.usecase.mypage.GetMyIdentityKeywordsUseCase
import com.umc.edison.domain.usecase.mypage.GetMyInterestKeywordUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
//    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val getMyIdentityKeywordsUseCase: GetMyIdentityKeywordsUseCase,
    private val getMyInterestKeywordUseCase: GetMyInterestKeywordUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(MyPageState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        initMyPage()
    }

    private fun initMyPage() {
        fetchProfileInfo()
        fetchMyIdentityKeyword()
        fetchInterestKeyword()
        fetchScrapBoard()
    }

    private fun fetchProfileInfo() {

    }

    private fun fetchMyIdentityKeyword() {
        collectDataResource(
            flow = getMyIdentityKeywordsUseCase(),
            onSuccess = { identityKeywords ->
                _uiState.update { state ->
                    state.copy(identity = identityKeywords.map { it.toPresentation() })
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
            flow = getMyInterestKeywordUseCase(),
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

    }
}