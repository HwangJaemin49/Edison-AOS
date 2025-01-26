package com.umc.edison.domain.usecase.bubble

import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.repository.BubbleRepository
import javax.inject.Inject

class DeleteBubblesUseCase @Inject constructor(
    private val bubbleRepository: BubbleRepository
) {
    operator fun invoke(bubbles: List<Bubble>) = bubbleRepository.deleteBubbles(bubbles)
}