package com.umc.edison.presentation.login

import androidx.compose.foundation.pager.PagerState
import androidx.navigation.NavHostController
import com.umc.edison.domain.model.identity.IdentityCategory
import com.umc.edison.domain.usecase.identity.GetIdentityByCategoryUseCase
import com.umc.edison.domain.usecase.identity.AddUserIdentityUseCase
import com.umc.edison.presentation.ToastManager
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.KeywordModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IdentityTestViewModel @Inject constructor(
    toastManager: ToastManager,
    private val addUserIdentityUseCase: AddUserIdentityUseCase,
    private val getIdentityByCategoryUseCase: GetIdentityByCategoryUseCase
) : BaseViewModel(toastManager) {
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
            3 -> IdentityCategory.INSPIRATION
            else -> return
        }
        getInterestKeyWords(category)
    }

    private fun getInterestKeyWords(interestCategory: IdentityCategory) {
        collectDataResource(
            flow = getIdentityByCategoryUseCase(
                category = interestCategory
            ),
            onSuccess = { interest ->
                _uiState.update {
                    it.copy(identity = interest.toPresentation())
                }
            },
        )
    }

    private fun getIdentityKeyWords(identityCategory: IdentityCategory) {
        collectDataResource(
            flow = getIdentityByCategoryUseCase(
                category = identityCategory,
            ),
            onSuccess = { identity ->
                _uiState.update {
                    it.copy(identity = identity.toPresentation())
                }
            },
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
                showToast("최대 5개의 키워드를 선택할 수 있습니다.")
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

    // TODO: Implement this function
    fun toggleInterestKeyword(keyword: KeywordModel) {
//        if (uiState.value.identity.selectedKeywords.contains(keyword)) {
//            _uiState.update {
//                it.copy(
//                    identity = it.identity.copy(
//                        selectedKeywords = it.identity.selectedKeywords - keyword
//                    )
//                )
//            }
//        } else {
//            if (uiState.value.identity.selectedKeywords.size >= 5) {
//                _baseState.update {
//                    it.copy(toastMessage = "최대 5개의 키워드를 선택할 수 있습니다.")
//                }
//            } else {
//                _uiState.update {
//                    it.copy(
//                        interest = it.interest.copy(
//                            selectedKeywords = it.interest.selectedKeywords + keyword
//                        )
//                    )
//                }
//            }
//        }
    }

    // TODO: Implement this function
    fun setInterestTestResult(navController: NavHostController) {
//        collectDataResource(
//            flow = setUserInterestUseCase(_uiState.value.interest.toDomain()),
//            onSuccess = {
//                _uiState.update { it.copy(interest = it.interest.copy(options = it.interest.selectedKeywords)) }
//                CoroutineScope(Dispatchers.Main).launch {
//                    navController.navigate(NavRoute.MyEdison.route)
//                }
//            },
//            onError = { error ->
//                _baseState.update { it.copy(toastMessage = "$error") }
//            },
//        )
    }

    fun setIdentityTestResult(
        pagerState: PagerState,
        coroutineScope: CoroutineScope
    ) {
        collectDataResource(
            flow = addUserIdentityUseCase(_uiState.value.identity.toDomain()),
            onSuccess = {
                _uiState.update { it.copy(identity = it.identity.copy(options = it.identity.selectedKeywords)) }
                coroutineScope.launch {
                    if (pagerState.currentPage < 3) {
                        pagerState.scrollToPage(pagerState.currentPage + 1)
                    }
                }

            },
        )
    }
}
