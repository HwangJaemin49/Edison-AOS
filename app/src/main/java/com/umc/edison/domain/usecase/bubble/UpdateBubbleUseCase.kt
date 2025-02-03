package com.umc.edison.domain.usecase.bubble

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateBubbleUseCase @Inject constructor(
    private val bubbleRepository: BubbleRepository
) {
    operator fun invoke(bubble: Bubble): Flow<DataResource<Unit>> {
        return bubbleRepository.updateBubbles(listOf(bubble))
    }
}
