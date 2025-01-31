package com.umc.edison.presentation.mypage

import com.umc.edison.domain.usecase.mypage.GetMyIdentityKeywordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
//    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val getMyIdentityKeywordsUseCase: GetMyIdentityKeywordsUseCase,
) {
    private val _uiState = MutableStateFlow(MyPageState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    init {
        initMyPage()
    }

    private fun initMyPage() {

    }

    private fun fetchProfileInfo() {

    }

    private fun fetchMyIdentityKeyword() {

    }

    private fun fetchScrapBoard() {

    }
}