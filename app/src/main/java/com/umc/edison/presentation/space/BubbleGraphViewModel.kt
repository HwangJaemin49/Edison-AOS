package com.umc.edison.presentation.space

import com.umc.edison.domain.usecase.space.GetClusteredBubblesUseCase
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
    private val getClusteredBubblesUseCase: GetClusteredBubblesUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(BubbleGraphState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun fetchClusteredBubbles() {
        collectDataResource(
            flow = getClusteredBubblesUseCase(),
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
