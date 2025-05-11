package com.umc.edison.domain.usecase.bubble

import com.umc.edison.data.DataResource
import com.umc.edison.domain.model.bubble.Bubble
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrashBubblesUseCase @Inject constructor(
    private val bubbleRepository: BubbleRepository
) {
    operator fun invoke(bubbles: List<Bubble>): Flow<DataResource<Unit>> =
        bubbleRepository.softDeleteBubbles(bubbles)
}