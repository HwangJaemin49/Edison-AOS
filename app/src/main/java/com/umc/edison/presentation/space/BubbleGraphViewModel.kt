package com.umc.edison.presentation.space

import com.umc.edison.domain.usecase.space.GetClusteredBubblesUseCase
import com.umc.edison.presentation.base.BaseViewModel
import com.umc.edison.presentation.model.EdgeDataModel
import com.umc.edison.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BubbleGraphViewModel @Inject constructor(
    private val getClusteredBubblesUseCase: GetClusteredBubblesUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(BubbleGraphState.DEFAULT)
    val uiState = _uiState.asStateFlow()

    fun fetchClusteredBubbles() {
        collectDataResource(
            flow = getClusteredBubblesUseCase(),
            onSuccess = { clusteredBubbles ->
                val edges = mutableListOf<EdgeDataModel>()

                clusteredBubbles.forEach { bubblePosition ->
                    bubblePosition.bubble.linkedBubble?.let {
                        edges.add(
                            EdgeDataModel(
                                startBubbleId = bubblePosition.bubble.id,
                                endBubbleId = it.id
                            )
                        )
                    }

                    bubblePosition.bubble.backLinks.forEach { backLink ->
                        edges.add(
                            EdgeDataModel(
                                startBubbleId = bubblePosition.bubble.id,
                                endBubbleId = backLink.id
                            )
                        )
                    }
                }

                // edges의 중복 제거 (startBubbleId, endBubbleId가 같은 경우 혹은 쌍을 뒤집은 경우 같으면 중복 제거)
                val uniqueEdges = edges.distinctBy { edge ->
                    if (edge.startBubbleId < edge.endBubbleId) {
                        edge
                    } else {
                        edge.copy(
                            startBubbleId = edge.endBubbleId,
                            endBubbleId = edge.startBubbleId
                        )
                    }
                }

                _uiState.update {
                    it.copy(
                        bubbles = clusteredBubbles.toPresentation(),
                        edges = uniqueEdges
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