package com.umc.edison.domain.usecase.bubble

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteBubblesUseCase @Inject constructor(
    private val bubbleRepository: BubbleRepository
) {
    operator fun invoke(bubbles: List<Bubble>): Flow<DataResource<Unit>> = bubbleRepository.deleteBubbles(bubbles)
}