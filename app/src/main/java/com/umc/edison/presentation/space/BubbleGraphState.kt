package com.umc.edison.presentation.space

import com.umc.edison.presentation.base.BaseState
import com.umc.edison.presentation.model.ClusterModel
import com.umc.edison.presentation.model.EdgeDataModel
import com.umc.edison.presentation.model.PositionedBubbleModel

data class BubbleGraphState(
    val clusters: List<ClusterModel>,
    val bubbles: List<PositionedBubbleModel>,
    val edges: List<EdgeDataModel>,
    override val isLoading: Boolean,
    override val error: Throwable?,
    override val toastMessage: String?
) : BaseState {
    companion object {
        val DEFAULT = BubbleGraphState(
            clusters = emptyList(),
            bubbles = emptyList(),
            edges = emptyList(),
            isLoading = false,
            error = null,
            toastMessage = null
        )
    }
}
