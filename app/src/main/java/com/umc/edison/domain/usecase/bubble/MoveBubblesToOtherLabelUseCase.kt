package com.umc.edison.domain.usecase.bubble

import com.umc.edison.data.DataResource
import com.umc.edison.domain.model.bubble.Bubble
import com.umc.edison.domain.model.label.Label
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoveBubblesToOtherLabelUseCase @Inject constructor(
    private val bubbleRepository: BubbleRepository,
) {
    operator fun invoke(
        bubbles: List<Bubble>,
        moveFrom: Label,
        moveTo: Label
    ): Flow<DataResource<Unit>> {
        val updatedBubbles = bubbles.map { bubble ->
            val newLabels = bubble.labels.filter { label ->
                label.id != moveFrom.id
            }
            bubble.copy(
                labels = newLabels + moveTo
            )
        }

        return bubbleRepository.updateBubbles(updatedBubbles)
    }
}