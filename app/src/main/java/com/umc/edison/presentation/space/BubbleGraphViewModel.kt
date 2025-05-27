package com.umc.edison.presentation.space

import com.umc.edison.domain.usecase.bubble.GetAllClusteredBubblesUseCase
import com.umc.edison.presentation.ToastManager
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.toClusterModel
import com.umc.edison.presentation.model.toEdgeDataModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BubbleGraphViewModel @Inject constructor(
    toastManager: ToastManager,
    private val getAllClusteredBubblesUseCase: GetAllClusteredBubblesUseCase,
) : BaseViewModel(toastManager) {
    private val _uiState = MutableStateFlow(BubbleGraphState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun fetchClusteredBubbles() {
        collectDataResource(
            flow = getAllClusteredBubblesUseCase(),
            onSuccess = { clusteredBubbles ->
                val bubbles =
                    clusteredBubbles.toPresentation().sortedByDescending { it.bubble.date }
                val edges = bubbles.toEdgeDataModel()
                val clusters = bubbles.toClusterModel()

                _uiState.update {
                    it.copy(
                        bubbles = bubbles,
                        edges = edges,
                        clusters = clusters
                    )
                }
            },
        )
    }
}
