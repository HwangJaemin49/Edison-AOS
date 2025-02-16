package com.umc.edison.presentation.login

import android.util.Log
import androidx.compose.foundation.pager.PagerState
import androidx.navigation.NavHostController
import com.umc.edison.domain.model.IdentityCategory
import com.umc.edison.domain.model.InterestCategory
import com.umc.edison.domain.usecase.login.GetIdentityKeywordsByCategoryUseCase
import com.umc.edison.domain.usecase.login.GetInterestKeywordsByCategoryUseCase
import com.umc.edison.domain.usecase.login.SetUserIdentityUseCase
import com.umc.edison.domain.usecase.login.SetUserInterestUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.KeywordModel
import com.umc.edison.presentation.model.toPresentation
import com.umc.edison.ui.navigation.NavRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IdentityTestViewModel @Inject constructor (
    private val setUserInterestUseCase: SetUserInterestUseCase,
    private val setUserIdentityUseCase: SetUserIdentityUseCase,
    private val getInterestKeywordsByCategoryUseCase: GetInterestKeywordsByCategoryUseCase,
    private val getIdentityKeywordsByCategoryUseCase: GetIdentityKeywordsByCategoryUseCase
): BaseViewModel(

) {
    private val _uiState = MutableStateFlow(IdentityTestState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun updateTabIndex(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }

        if (index < 3) {
            getIdentityCategoryForTab(index)
        } else {
            getInterestCategoryForTab(index)
        }
    }

    private fun getIdentityCategoryForTab(index: Int) {

        val category = when (index) {
            0 -> IdentityCategory.EXPLAIN
            1 -> IdentityCategory.FIELD
            2 -> IdentityCategory.ENVIRONMENT
            else -> return
        }
        getIdentityKeyWords(category)
    }

    private fun getInterestCategoryForTab(index: Int) {
        val category = when (index) {
            3 -> InterestCategory.INSPIRATION
            else -> return
        }
        getInterestKeyWords(category)
    }

    private fun getInterestKeyWords(interestCategory: InterestCategory) {

        collectDataResource(
            flow = getInterestKeywordsByCategoryUseCase(
                interestCategory =  interestCategory

            ),
            onSuccess = {
                interest ->
                _uiState.update {
                    it.copy(interest = interest.toPresentation())
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

    private fun getIdentityKeyWords(identityCategory : IdentityCategory) {

        collectDataResource(
            flow = getIdentityKeywordsByCategoryUseCase(
                identityCategory = identityCategory,
            ),
            onSuccess = {
                    identity ->
                _uiState.update {
                    it.copy(identity = identity.toPresentation())
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

    fun toggleIdentityKeyword(keyword: KeywordModel) {
        if (uiState.value.identity.selectedKeywords.contains(keyword)) {
            _uiState.update {
                it.copy(
                    identity = it.identity.copy(
                        selectedKeywords = it.identity.selectedKeywords - keyword
                    )
                )
            }
        } else {
            if (uiState.value.identity.selectedKeywords.size >= 5) {
                _uiState.update {
                    it.copy(toastMessage = "최대 5개의 키워드를 선택할 수 있습니다.")
                }
            } else {
                _uiState.update {
                    it.copy(
                        identity = it.identity.copy(
                            selectedKeywords = it.identity.selectedKeywords + keyword
                        )
                    )
                }
            }
        }
    }

    fun toggleInterestKeyword(keyword: KeywordModel) {
        if (uiState.value.interest.selectedKeywords.contains(keyword)) {
            _uiState.update {
                it.copy(
                    interest = it.interest.copy(
                        selectedKeywords = it.interest.selectedKeywords - keyword
                    )
                )
            }
        } else {
            if (uiState.value.interest.selectedKeywords.size >= 5) {
                _uiState.update {
                    it.copy(toastMessage = "최대 5개의 키워드를 선택할 수 있습니다.")
                }
            } else {
                _uiState.update {
                    it.copy(
                        interest = it.interest.copy(
                            selectedKeywords = it.interest.selectedKeywords + keyword
                        )
                    )
                }
            }
        }
    }

    fun setInterestTestResult(navController: NavHostController){
        collectDataResource(
            flow =setUserInterestUseCase(_uiState.value.interest.toDomain()),
            onSuccess = {
                _uiState.update { it.copy(interest = it.interest.copy(options = it.interest.selectedKeywords)) }
                CoroutineScope(Dispatchers.Main).launch {
                    navController.navigate(NavRoute.MyEdison.route)
                }
            },
            onError = { error ->
                _uiState.update { it.copy(toastMessage = "$error",error = error) }
            },
            onLoading = {
                _uiState.update { it.copy(isLoading = true) }
            },
            onComplete = {
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    fun setIdentityTestResult(pagerState: PagerState,
                              coroutineScope: CoroutineScope ){
        collectDataResource(
            flow = setUserIdentityUseCase(_uiState.value.identity.toDomain()),
            onSuccess = {
                _uiState.update { it.copy(identity = it.identity.copy(options = it.identity.selectedKeywords)) }
                coroutineScope.launch {
                    if (pagerState.currentPage < 3) {
                        pagerState.scrollToPage(pagerState.currentPage + 1)
                    }
                }

            },
            onError = { error ->
                _uiState.update { it.copy(toastMessage = "$error", error = error) }
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